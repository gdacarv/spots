package com.dcc.matc89.spots.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Sport;
import com.dcc.matc89.spots.model.Spot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SpotDetailActivity extends ActionBarActivity {
	
	public static final String SPOT_KEY = "spotkey";
	
	private Spot mSpot;
	
	private TextView mDescription, mGroupsCount;
	private View mGroups;
	private LinearLayout mSports, mPics;
	private ImageView mapImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spot_detail);
		mSpot = (Spot) getIntent().getSerializableExtra(SPOT_KEY);
		
		mapImage = (ImageView) findViewById(R.id.mapSnapshot);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapSpot);
		final GoogleMap map = mapFragment.getMap();
		
		if (map != null) {
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			Marker spotMarker = map.addMarker(new MarkerOptions()
					.position(mSpot.getLatLng())
					.title(mSpot.getName())
					.snippet(mSpot.getDescription()));
			spotMarker.showInfoWindow();
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mSpot.getLatitude() + 0.001,mSpot.getLongitude()), 15));

			
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
			//TODO check map unloading for possible memory leaks.
			

		}
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		setupViews();
	}

	private void setupViews() {
		mDescription = (TextView) findViewById(R.id.txt_spot_description);
		mGroupsCount = (TextView) findViewById(R.id.txt_spot_groups);
		mGroups = findViewById(R.id.view_spot_groups);
		mPics = (LinearLayout) findViewById(R.id.list_spot_pictures);
		mSports = (LinearLayout) findViewById(R.id.list_spot_sports);
		
		mDescription.setText(mSpot.getDescription());
		mGroupsCount.setText(String.valueOf(mSpot.getGroupsCount()));
		mGroups.setOnClickListener(onGroupsClickListener);
		
		for (int i = 0; i < 4; i = i + 1){
			ImageView picture = new ImageView(this);
			picture.setImageDrawable(getResources().getDrawable(android.R.drawable.gallery_thumb));
			picture.setPadding(5, 2, 5, 2);
			mPics.addView(picture, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		
		List<Sport> sports = mSpot.getSports();
		for (int i = 0; i < sports.size(); i = i + 1) {
			TextView sportItem = new TextView(this);
			sportItem.setText(sports.get(i).toString());
			sportItem.setPadding(2, 5, 2, 5);
			mSports.addView( sportItem, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT) );
		}
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(mSpot.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spot_detail, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}
	
	private OnClickListener onGroupsClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(SpotDetailActivity.this, GroupListActivity.class);
			i.putExtra(GroupListActivity.SPOT_KEY, mSpot);
			startActivity(i);
		}
	};

}
