package com.dcc.matc89.spots.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import com.dcc.matc89.spots.model.User;
import com.dcc.matc89.spots.network.FetchGroups;
import com.dcc.matc89.spots.network.FetchGroups.OnGroupsReceiver;

/** Same of GroupsListActivity. This activity shouldn't exist because it is a copy of GroupsListActivity plus Login functionality. */
public class MyGroupsListActivity extends LoginActionBarActivity {

	private static final int CODE_ADD_GROUP = 1;
	public static final String SPOT_KEY = "spot_key";
	public static final String USER_KEY = "user_key";
	public static final String GROUP_SELECTED = "group_selected";
	public static final String SELECT_GROUP = "select_group";

	private TextView mTextEmpty;
	private View mProgressLoading;
	private ListView mListView;

	private Spot mSpot;
	private User mUser;
	private boolean mIsSelectingGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);

		mTextEmpty = (TextView) findViewById(R.id.group_list_empty);
		mProgressLoading = findViewById(R.id.pgs_groups);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(onItemClickListener);

		mSpot = null;
		mUser = User.getCurrentUser(this);

		mIsSelectingGroup = getIntent().getBooleanExtra(SELECT_GROUP, false);

		// Show the Up button in the action bar.
		setupActionBar();

	}

	@Override
	protected void onResume() {
		super.onResume();

		loadGroups();
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
		if(!mIsSelectingGroup)
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
			Serializable group = (Serializable) adapterView.getAdapter().getItem(position);
			if(mIsSelectingGroup){
				setResult(RESULT_OK, new Intent().putExtra(GROUP_SELECTED, group));
				finish();
			} else{
				Intent i = new Intent(MyGroupsListActivity.this, GroupDetailActivity.class);
				i.putExtra(GroupDetailActivity.GROUP_KEY, group);
				startActivity(i);
			}
		}
	};

	private void showGroups(List<Group> result) {
		mProgressLoading.setVisibility(View.INVISIBLE);
		if(result == null || result.isEmpty())
			mTextEmpty.setVisibility(View.VISIBLE);
		else{
			mTextEmpty.setVisibility(View.INVISIBLE);
			ListAdapter adapter = new ArrayAdapter<Group>(MyGroupsListActivity.this, android.R.layout.simple_list_item_1, result);
			mListView.setAdapter(adapter);
		}
	}

	private void loadGroups() {
		if(mSpot != null){
			new FetchGroups().getGroupsFromSpot(onGroupsReceiver, mSpot.getId());
		} else if(mUser != null){
			new FetchGroups().getGroupsFromUser(onGroupsReceiver, mUser.getId());
		}
	}

	private OnGroupsReceiver onGroupsReceiver = new OnGroupsReceiver() {

		@Override
		public void onGroupsReceived(List<Group> groups) {
			showGroups(groups);
		}
	};

	@Override
	protected void onLoggedIn(User user) {
		mUser = user;
		loadGroups();
	}

}
