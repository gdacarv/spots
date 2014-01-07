package com.dcc.matc89.spots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.model.User;

public class GroupDetailActivity extends ActionBarActivity {
	
	public static final String GROUP_KEY = "groupkey";
	
	private Group mGroup;
	
	private TextView mDescription, mSport, mUsersCount, mSpotsCount, mJoin;
	private View mUsers, mSpots;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_detail);
		mGroup = (Group) getIntent().getSerializableExtra(GROUP_KEY);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		setupViews();
	}

	private void setupViews() {
		mDescription = (TextView) findViewById(R.id.txt_group_description);
		mSport = (TextView) findViewById(R.id.txt_group_sport);
		mUsersCount = (TextView) findViewById(R.id.txt_group_users);
		mSpotsCount = (TextView) findViewById(R.id.txt_group_spots);
		mUsers = findViewById(R.id.view_group_users);
		mSpots = findViewById(R.id.view_group_spots);
		mJoin = (TextView) findViewById(R.id.btn_group_join);
		User user = User.getCurrentUser(this);
		if(user == null)
			mJoin.setVisibility(View.GONE);
		else{
			if(mGroup.containsUser(user))
				mJoin.setText(R.string.leave_group);
			else
				mJoin.setText(R.string.join_group);
		}
		
		
		mDescription.setText(mGroup.getDescription());
		mSport.setText(mGroup.getSport().getName());
		mUsersCount.setText(String.valueOf(mGroup.getMembersCount()));
		mSpotsCount.setText(String.valueOf(mGroup.getSpotsCount()));
		mUsers.setOnClickListener(onUsersClickListener);
		mSpots.setOnClickListener(onSpotsClickListener);
		mJoin.setOnClickListener(onJoinClickListener);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(mGroup.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_detail, menu);
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
	
	private OnClickListener onUsersClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(GroupDetailActivity.this, UserListActivity.class);
			i.putExtra(UserListActivity.GROUP_KEY, mGroup);
			startActivity(i);
		}
	};
	
	private OnClickListener onSpotsClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(GroupDetailActivity.this, SpotListActivity.class);
			i.putExtra(SpotListActivity.GROUP_KEY, mGroup);
			startActivity(i);
		}
	};
	
	private OnClickListener onJoinClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(GroupDetailActivity.this, "Join clicked.", Toast.LENGTH_SHORT).show(); // TODO Remove this, and make real connections
		}
	};

}
