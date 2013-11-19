package com.dcc.matc89.spots.activity;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.R.id;
import com.dcc.matc89.spots.R.layout;
import com.dcc.matc89.spots.R.menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

//Para que a ActionBar funcione em todas as versões é necessário estender ActionBarActivity ao invés de Activity
public class SpotEditActivity extends ActionBarActivity {
	ListView l;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spot_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
        String[] list = { "A", "B", "C" };
        
        l = (ListView) findViewById(R.id.listview);
        l.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				switch (position) {
				case 0:
					Toast.makeText(SpotEditActivity.this, "AAAAAAAAA", Toast.LENGTH_SHORT).show();
				case 1:
					Intent gotoDDG = new Intent(Intent.ACTION_BUG_REPORT);
					startActivity(gotoDDG);
				}
			}
        	
        });
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
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
		}
		return super.onOptionsItemSelected(item);
	}

}
