package com.dcc.matc89.spots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.dcc.matc89.spots.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

// Para que a ActionBar funcione em todas as versões é necessário estender ActionBarActivity ao invés de Activity
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		GoogleMap map = mapFragment.getMap();

		if(map != null){
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			LatLng myLocation = new LatLng(-12.97, -38.51);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,14));
			
			//TODO estudar LocationClient API e google play services http://developer.android.com/training/location/retrieve-current.html
			//map.setMyLocationEnabled(true);
			//LatLng myLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,10));
		}

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
			Intent i2 = new Intent(this, GroupListActivity.class);
			startActivity(i2);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
