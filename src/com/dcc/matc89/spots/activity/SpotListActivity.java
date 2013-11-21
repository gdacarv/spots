package com.dcc.matc89.spots.activity;

import java.util.List;

import android.annotation.TargetApi;
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

public class SpotListActivity extends ActionBarActivity {

	public static final String GROUP_KEY = "group_key";
	
	private TextView mTextEmpty;
	private View mProgressLoading;
	private ListView mListView;
	
	private Group mGroup;

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
		
		loadSpotsAsync();
	}

	/** This method have to load the users. This can takes as long as it need (ie. You can do network requests here). */
	private List<Spot> loadSpotsSync() {
		// TODO Get from WEB API OR from Group object
		SystemClock.sleep(3000); // Simulates web request. Remove when use real WEB API
		
		return mGroup.getSpots();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void loadSpotsAsync() {
		AsyncTask<Void, Void, List<Spot>> task = new SpotLoadAsyncTask();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			task.execute();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//TODO change to spot_list
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
			/*Intent i = new Intent(UserListActivity.this, UserDetailActivity.class);
			i.putExtra(SpotDetailActivity.SPOT_KEY, (Serializable) adapterView.getAdapter().getItem(position));
			startActivity(i);*/
		}
	};

	private class SpotLoadAsyncTask extends AsyncTask<Void, Void, List<Spot>> {
		
		@Override
		protected List<Spot> doInBackground(Void... params) {
			List<Spot> spots = loadSpotsSync();
			return spots;
		}
		
		@Override
		protected void onPostExecute(List<Spot> result) {
			super.onPostExecute(result);
			mProgressLoading.setVisibility(View.INVISIBLE);
			if(result == null || result.isEmpty())
				mTextEmpty.setVisibility(View.VISIBLE);
			else{
				ListAdapter adapter = new ArrayAdapter<Spot>(SpotListActivity.this, android.R.layout.simple_list_item_1, result);
				mListView.setAdapter(adapter);
			}
		}
	}
}
