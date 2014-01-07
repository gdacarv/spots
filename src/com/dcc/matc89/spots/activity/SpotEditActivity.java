package com.dcc.matc89.spots.activity;

import java.util.Arrays;
import java.util.List;

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

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Sport;
import com.dcc.matc89.spots.model.Spot;
import com.dcc.matc89.spots.network.FetchSports;
import com.dcc.matc89.spots.network.FetchSports.OnSportsReceiver;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

//Para que a ActionBar funcione em todas as vers�es � necess�rio estender ActionBarActivity ao inv�s de Activity
public class SpotEditActivity extends LoginActionBarActivity {
		
	private Spot mSpot;
	private Spinner mSport, mPics;
	private ImageView mapImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spot_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
        setupViews();
	}

	private void setupViews() {
		mSport = (Spinner) findViewById(R.id.spn_editspot_sport);
		mPics = (Spinner) findViewById(R.id.spn_editspot_pics);
		
		new FetchSports().getSports(onSportsReceiver);
		
		List<String> pictures = Arrays.asList("Pictures", "Add More", "Feature still to come");
		SpinnerAdapter picsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pictures);
		mPics.setAdapter(picsAdapter);
		
		//TODO change this to location clicked on main activity before adding spot or to the spot being edited.
		//mSpot = StaticDatabase.getSingleton().getSpots().get(0);
		
		mapImage = (ImageView) findViewById(R.id.mapSnapshotEdit);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapSpotEdit);
		final GoogleMap map = mapFragment.getMap();
		
		if (map != null) {
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			//map.addMarker(new MarkerOptions().position(mSpot.getLatLng()));
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mSpot.getLatitude() + 0.001,mSpot.getLongitude()), 15));

			
			map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			    public void onMapLoaded() {
			        map.snapshot(new GoogleMap.SnapshotReadyCallback() {
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
			// TODO Send new spot OR update spot to WEB API
			setResult(RESULT_OK);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
