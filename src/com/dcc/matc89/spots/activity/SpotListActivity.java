package com.dcc.matc89.spots.activity;

import java.io.Serializable;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.model.Spot;
import com.dcc.matc89.spots.network.FetchSpots;
import com.dcc.matc89.spots.network.FetchSpots.OnSpotsReceiver;

public class SpotListActivity extends ActionBarActivity {

	public static final String GROUP_KEY = "group_key";
	
	private TextView mTextEmpty;
	private View mProgressLoading;
	private ListView mListView;
	
	private Group mGroup;
	protected List<Spot> mSpots;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spot_list);
		
		mTextEmpty = (TextView) findViewById(R.id.spot_list_empty);
		mProgressLoading = findViewById(R.id.pgs_spot);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(onItemClickListener);
		
		mGroup = (Group) getIntent().getSerializableExtra(GROUP_KEY);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		loadSpots();
	}

	private OnSpotsReceiver receiver = new OnSpotsReceiver() {
		@Override
		public void onSpotsReceived(List<Spot> result) {
			mProgressLoading.setVisibility(View.INVISIBLE);
			if(result == null || result.isEmpty())
				mTextEmpty.setVisibility(View.VISIBLE);
			else{
				ListAdapter adapter = new ArrayAdapter<Spot>(SpotListActivity.this, android.R.layout.simple_list_item_1, result);
				mListView.setAdapter(adapter);
			}
		}
	};
	
	/** This method have to load the users. This can takes as long as it need (ie. You can do network requests here). */
	private void loadSpots() {
		new FetchSpots().getSpots(receiver, mGroup.getId());
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.spot_list, menu);
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
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			// TODO Send user to SpotDetail activity
			Intent i = new Intent(SpotListActivity.this, SpotDetailActivity.class);
			i.putExtra(SpotDetailActivity.SPOT_KEY, (Serializable) adapterView.getAdapter().getItem(position));
			startActivity(i);
		}
	};
}
