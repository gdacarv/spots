package com.dcc.matc89.spots.activity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.model.Sport;
import com.dcc.matc89.spots.model.Spot;
import com.dcc.matc89.spots.model.User;
import com.dcc.matc89.spots.network.FetchSports;
import com.dcc.matc89.spots.network.FetchSports.OnSportsReceiver;
import com.dcc.matc89.spots.network.PostSpots;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//Para que a ActionBar funcione em todas as vers�es � necess�rio estender ActionBarActivity ao inv�s de Activity
public class SpotEditActivity extends LoginActionBarActivity {
		
	private Spinner mSport, mPics;
	private ImageView mapImage;
	private Marker mMarker;
	private GoogleMap mMap;
	private TextView mNameText, mDescriptionText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spot_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
        setupViews();
	}

	private void setupViews() {
		mNameText = (TextView) findViewById(R.id.txt_editspot_name);
		mDescriptionText = (TextView) findViewById(R.id.txt_editspot_description);
		mSport = (Spinner) findViewById(R.id.spn_editspot_sport);
		mPics = (Spinner) findViewById(R.id.spn_editspot_pics);
		mPics.setEnabled(false);
		
		new FetchSports().getSports(onSportsReceiver);
		
		List<String> pictures = Arrays.asList("Pictures", "Add More", "Feature still to come");
		SpinnerAdapter picsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pictures);
		mPics.setAdapter(picsAdapter);
		
		mapImage = (ImageView) findViewById(R.id.mapSnapshotEdit);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapSpotEdit);
		mMap = mapFragment.getMap();
		
		if (mMap != null) {
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			mMap.setMyLocationEnabled(true);
			mMap.setOnMapClickListener(onMapClickListener);
			//map.addMarker(new MarkerOptions().position(mSpot.getLatLng()));
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mSpot.getLatitude() + 0.001,mSpot.getLongitude()), 15));

			
			mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			    public void onMapLoaded() {
			        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
			            @SuppressWarnings("deprecation")
						public void onSnapshotReady(Bitmap bitmap) {
			                mapImage.setAlpha(1);
			                mapImage.setImageBitmap(bitmap);
			            }
			        });
			    }
			});
		}
			//TODO check map unloading for possible memory leaks.
	}
	
	private OnSportsReceiver onSportsReceiver = new OnSportsReceiver() {
		
		@Override
		public void onSportsReceived(List<Sport> sports) {
			setupSportSpinner(sports);
			findViewById(R.id.pgs_sports).setVisibility(View.INVISIBLE);
		}
	};

	private void setupSportSpinner(List<Sport> sports) {
		SpinnerAdapter sportsAdapter = new ArrayAdapter<Sport>(this, android.R.layout.simple_spinner_item, sports);
		mSport.setAdapter(sportsAdapter);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	private void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle(R.string.new_spot);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spot_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_save:
			saveSpot();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveSpot() {
		String name = mNameText.getText().toString();
		if(name == null || name.length() == 0){
			Toast.makeText(this, R.string.you_must_provide_a_name_, Toast.LENGTH_SHORT).show();
			return;
		}
		String description = mDescriptionText.getText().toString();
		if(description == null || description.length() == 0){
			Toast.makeText(this, R.string.you_must_provide_a_description_, Toast.LENGTH_SHORT).show();
			return;
		}
		if(mMarker == null){
			Toast.makeText(this, R.string.click_on_the_map_to_mark_the_spot_s_location_, Toast.LENGTH_SHORT).show();
			return;
		}
		Spot spot = new Spot(-1, name, description, Collections.<Long>emptyList(), Arrays.asList((Sport)mSport.getSelectedItem()), mMarker.getPosition().latitude, mMarker.getPosition().longitude);
		new PostSpots().newSpot(null, spot, User.getCurrentUser(this).getId());
		Intent intent = new Intent(this, SpotDetailActivity.class);
		intent.putExtra(SpotDetailActivity.SPOT_KEY, spot);
		startActivity(intent);
		setResult(RESULT_OK);
		finish();
	}
	
	private OnMapClickListener onMapClickListener = new OnMapClickListener() {
		
		@Override
		public void onMapClick(LatLng arg0) {
			if(mMarker == null){
				mMarker = mMap.addMarker(new MarkerOptions().draggable(true).position(arg0));
			} else
				mMarker.setPosition(arg0);
		}
	};

	@Override
	protected void onLoggedIn(User user) {
	}

}
