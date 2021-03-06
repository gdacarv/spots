package com.dcc.matc89.spots.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Sport;
import com.dcc.matc89.spots.model.Spot;
import com.dcc.matc89.spots.network.FetchSpots;
import com.dcc.matc89.spots.network.FetchSpots.OnSpotsReceiver;
import com.dcc.matc89.spots.view.CheckboxesDropdownView;
import com.dcc.matc89.spots.view.CheckboxesDropdownView.OnDropdownCheckedChangeListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// Para que a ActionBar funcione em todas as vers���es ��� necess���rio estender ActionBarActivity ao inv���s de Activity
public class MainActivity extends ActionBarActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private LocationClient mLocationClient;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private Location mCurrentLocation;
	private Map<Marker,Spot> mMarkerSpot;
	private List<Sport> mSports;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = mapFragment.getMap();
		if(map != null)
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		if (servicesConnected()){
			mLocationClient = new LocationClient(this,this,this);
		}

	}

	@Override
	protected void onStart(){
		super.onStart();
		if(mLocationClient != null)
			mLocationClient.connect();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		loadSpots();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_spot:
			Intent i = new Intent(this, SpotEditActivity.class);
			startActivity(i);
			break;
		case R.id.action_groups:
			Intent i2 = new Intent(this, MyGroupsListActivity.class);
			startActivity(i2);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()){
			try {
				connectionResult.startResolutionForResult(this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		} else {
			showErrorDialog(connectionResult.getErrorCode());
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
			case CONNECTION_FAILURE_RESOLUTION_REQUEST :
				switch(resultCode) {
					case Activity.RESULT_OK :
						
					break;
				}
			}
	}

	private void showErrorDialog(int errorCode) {
		Toast.makeText(this, "Connection Error ".concat(String.valueOf(errorCode)), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		mCurrentLocation = mLocationClient.getLastLocation();
		map.setMyLocationEnabled(true);
		
		if (mCurrentLocation != null) {
			LatLng mLocationLatLng = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocationLatLng, 15));
		}
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
 	protected void onStop(){
		if(mLocationClient != null)
			mLocationClient.disconnect();
		super.onStop();
	}
	
	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		ConnectionResult connectionResult = new ConnectionResult(resultCode, null);
		if (resultCode == ConnectionResult.SUCCESS) {
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			int errorCode = connectionResult.getErrorCode();
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
			if (errorDialog != null) {
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
				errorFragment.show(getSupportFragmentManager(), "Location Updates");
			}
		}
		return false;
	}
	
	public static class ErrorDialogFragment extends DialogFragment{
		private Dialog mDialog;
		
		public ErrorDialogFragment(){
			super();
			mDialog = null;
		}
		
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			return mDialog;
		}
	}

	private void loadSpots() {
		new FetchSpots().getSpots(onSpotsReceiver);
	}
	
	private OnSpotsReceiver onSpotsReceiver = new OnSpotsReceiver() {
		
		@Override
		public void onSpotsReceived(List<Spot> spots) {
			findViewById(R.id.pgs_map).setVisibility(View.INVISIBLE);
			mSports = getSports(spots);
			createMarkersFromSpots(spots);
			configureSportsPicker();
		}
	};

	private void createMarkersFromSpots(List<Spot> spots) {
		if(map != null){
			if(mMarkerSpot == null)
				mMarkerSpot = new HashMap<Marker, Spot>(spots.size());
			else{
				for(Entry<Marker,Spot> entry : mMarkerSpot.entrySet())
					entry.getKey().remove();
				mMarkerSpot.clear();
			}
			for(Spot spot : spots){
				mMarkerSpot.put(map.addMarker(new MarkerOptions()
						.position(new LatLng(spot.getLatitude(), spot.getLongitude()))
						.title(spot.getName())
						.snippet(spot.getDescription())), spot);
			}
			map.setOnInfoWindowClickListener(onInfoWindowClickListener);
		}
	}

	private OnInfoWindowClickListener onInfoWindowClickListener = new OnInfoWindowClickListener() {
		
		@Override
		public void onInfoWindowClick(Marker marker) {
			Intent i = new Intent(MainActivity.this, SpotDetailActivity.class);
			i.putExtra(SpotDetailActivity.SPOT_KEY, (Serializable) mMarkerSpot.get(marker));
			startActivity(i);
		}
	};

	private List<Sport> getSports(List<Spot> spots) {
		List<Sport> sports = new ArrayList<Sport>();
		for(Spot spot : spots)
			for(Sport sport : spot.getSports()) {
				boolean alreadyContains = false;
				for(Sport sportInList : sports)
					if(sportInList.getId() == sport.getId()){
						alreadyContains = true;
						break;
					}
				if(!alreadyContains)
					sports.add(sport);
			}
		return sports;
	}

	private void configureSportsPicker() {
		CheckboxesDropdownView picker = (CheckboxesDropdownView) findViewById(R.id.sport_picker);
		picker.setItems(new ArrayList<Sport>(mSports));
		picker.setCheckedAll(true);
		picker.setOnDropdownCheckedChangeListener(onDropdownCheckedChangeListener);
		picker.setVisibility(View.VISIBLE);
	}
	
	private OnDropdownCheckedChangeListener onDropdownCheckedChangeListener = new OnDropdownCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CheckboxesDropdownView picker, int index, boolean isChecked) {
			configureMarkersVisibility(picker.getState(), mSports);
		}
	};

	private void configureMarkersVisibility(boolean[] states, List<Sport> sports) {
		for(Entry<Marker,Spot> entry : mMarkerSpot.entrySet()){
			boolean visible = false;
			for(Sport sport : entry.getValue().getSports())
				visible |= getState(states, sports, sport);
			entry.getKey().setVisible(visible);
		}
	}

	private boolean getState(boolean[] states, List<Sport> sports, Sport sport) {
		for(int i = 0; i < sports.size(); i++)
			if(sports.get(i).getId() == sport.getId())
				return states[i];
		return false;
	}
}
