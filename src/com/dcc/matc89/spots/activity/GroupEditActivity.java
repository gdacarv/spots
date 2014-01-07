package com.dcc.matc89.spots.activity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.model.Sport;
import com.dcc.matc89.spots.model.User;
import com.dcc.matc89.spots.network.FetchGroups.OnGroupsReceiver;
import com.dcc.matc89.spots.network.FetchSports;
import com.dcc.matc89.spots.network.FetchSports.OnSportsReceiver;
import com.dcc.matc89.spots.network.PostGroups;

public class GroupEditActivity extends LoginActionBarActivity {
	
	//private EditText mName, mDescription;
	private Spinner mSport;
	private TextView mNameText, mDescriptionText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
		setupViews();
	}

	private void setupViews() {
		mNameText = (TextView) findViewById(R.id.txt_editgroup_name);
		mDescriptionText = (TextView) findViewById(R.id.txt_editgroup_description);
		mSport = (Spinner) findViewById(R.id.spn_editgroup_sport);
		
		new FetchSports().getSports(onSportsReceiver);
		
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
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		supportActionBar.setTitle(R.string.new_group);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_edit, menu);
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
			saveGroup();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveGroup() {
		String name = mNameText.getText().toString();
		if(name == null || name.length() == 0){
			Toast.makeText(this, R.string.you_must_provide_a_name_, Toast.LENGTH_SHORT).show();
			return;
		}
		String description = mDescriptionText.getText().toString();
		if(description == null || description.length() == 0){
			Toast.makeText(this, R.string.you_must_provide_a_description_, Toast.LENGTH_SHORT).show();
			return;
		}
		long userId = User.getCurrentUser(this).getId();
		final Group group = new Group(-1, name, description, Arrays.asList(Long.valueOf(userId)), Collections.<Long>emptyList(), (Sport) mSport.getSelectedItem());
		new PostGroups().newGroup(new OnGroupsReceiver() {
			
			@Override
			public void onGroupsReceived(List<Group> groups) {
				group.setId(groups.get(0).getId());
			}
		}, group, userId);
		Intent intent = new Intent(this, GroupDetailActivity.class);
		intent.putExtra(GroupDetailActivity.GROUP_KEY, group);
		startActivity(intent);
		setResult(RESULT_OK);
		finish();
	}

	@Override
	protected void onLoggedIn(User user) {
	}
}
