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
import com.dcc.matc89.spots.model.StaticDatabase;

public class GroupListActivity extends ActionBarActivity {

	private static final int CODE_ADD_GROUP = 1;
	public static final String SPOT_KEY = "spot_key";
	
	private TextView mTextEmpty;
	private View mProgressLoading;
	private ListView mListView;
	
	private Spot mSpot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);
		
		mTextEmpty = (TextView) findViewById(R.id.group_list_empty);
		mProgressLoading = findViewById(R.id.pgs_groups);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(onItemClickListener);
		
		mSpot = (Spot) getIntent().getSerializableExtra(SPOT_KEY);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		loadGroupsAsync();
	}

	/** This method have to load the groups of current user. This can takes as long as it need (ie. You can do network requests here). */
	private List<Group> loadGroupsSync() {
		// TODO Get from WEB API
		SystemClock.sleep(3000); // Simulates web request. Remove when use real WEB API
		return StaticDatabase.getSingleton().getGroups();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void loadGroupsAsync() {
		AsyncTask<Void, Void, List<Group>> task = new GroupLoadAsyncTask();
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
		getMenuInflater().inflate(R.menu.group_list, menu);
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
		case R.id.action_group_add:
			Intent i = new Intent(this, GroupEditActivity.class);
			startActivityForResult(i, CODE_ADD_GROUP);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == CODE_ADD_GROUP && resultCode == RESULT_OK){
			// TODO Reload list
		}
	}
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			Intent i = new Intent(GroupListActivity.this, GroupDetailActivity.class);
			i.putExtra(GroupDetailActivity.GROUP_KEY, (Serializable) adapterView.getAdapter().getItem(position));
			startActivity(i);
		}
	};

	private class GroupLoadAsyncTask extends AsyncTask<Void, Void, List<Group>> {
		
		@Override
		protected List<Group> doInBackground(Void... params) {
			List<Group> groups = loadGroupsSync();
			return groups;
		}
		
		@Override
		protected void onPostExecute(List<Group> result) {
			super.onPostExecute(result);
			mProgressLoading.setVisibility(View.INVISIBLE);
			if(result == null || result.isEmpty())
				mTextEmpty.setVisibility(View.VISIBLE);
			else{
				ListAdapter adapter = new ArrayAdapter<Group>(GroupListActivity.this, android.R.layout.simple_list_item_1, result);
				mListView.setAdapter(adapter);
			}
		}
	}
}
