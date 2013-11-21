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
import com.dcc.matc89.spots.model.User;

public class UserListActivity extends ActionBarActivity {

	public static final String GROUP_KEY = "group_key";
	
	private TextView mTextEmpty;
	private View mProgressLoading;
	private ListView mListView;
	
	private Group mGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		
		mTextEmpty = (TextView) findViewById(R.id.user_list_empty);
		mProgressLoading = findViewById(R.id.pgs_users);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(onItemClickListener);
		
		mGroup = (Group) getIntent().getSerializableExtra(GROUP_KEY);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		loadUsersAsync();
	}

	/** This method have to load the users. This can takes as long as it need (ie. You can do network requests here). */
	private List<User> loadUsersSync() {
		// TODO Get from WEB API OR from Group object
		SystemClock.sleep(3000); // Simulates web request. Remove when use real WEB API
		
		return mGroup.getUsers();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void loadUsersAsync() {
		AsyncTask<Void, Void, List<User>> task = new UserLoadAsyncTask();
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
		getMenuInflater().inflate(R.menu.user_list, menu);
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
			// TODO Send user o UserDetail (profile) activity
			Intent i = new Intent(UserListActivity.this, UserDetailActivity.class);
			i.putExtra(UserDetailActivity.USER_KEY, (Serializable) adapterView.getAdapter().getItem(position));
			startActivity(i);
		}
	};

	private class UserLoadAsyncTask extends AsyncTask<Void, Void, List<User>> {
		
		@Override
		protected List<User> doInBackground(Void... params) {
			List<User> users = loadUsersSync();
			return users;
		}
		
		@Override
		protected void onPostExecute(List<User> result) {
			super.onPostExecute(result);
			mProgressLoading.setVisibility(View.INVISIBLE);
			if(result == null || result.isEmpty())
				mTextEmpty.setVisibility(View.VISIBLE);
			else{
				ListAdapter adapter = new ArrayAdapter<User>(UserListActivity.this, android.R.layout.simple_list_item_1, result);
				mListView.setAdapter(adapter);
			}
		}
	}
}
