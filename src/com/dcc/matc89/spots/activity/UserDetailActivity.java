package com.dcc.matc89.spots.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.User;

public class UserDetailActivity extends ActionBarActivity {

	public static final String USER_KEY = "userkey";
	
	private ImageView mProfilePicture;
	private TextView mName;
	private TextView mLocation;
	private TextView mGroupsAmount;
	private User mData;
	private View mGroupsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_main);
		setupActionBar();
		
		mProfilePicture = (ImageView) findViewById(R.id.profile_picture);
		mName = (TextView) findViewById(R.id.name);
		mLocation = (TextView) findViewById(R.id.location);
		mGroupsAmount = (TextView) findViewById(R.id.groups_text);
		mGroupsButton = findViewById(R.id.groups_button);
		
		mData = (User) getIntent().getSerializableExtra(USER_KEY);
		
		// TODO Set profile picture
		mName.setText(mData.getName());
		mLocation.setText(mData.getLocation());
		mGroupsAmount.setText(String.valueOf(mData.getGroups().size()));
		mGroupsButton.setOnClickListener(onGroupClicked);
	}
	
	private OnClickListener onGroupClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO actually pass this user's list
			Intent i2 = new Intent(UserDetailActivity.this, GroupListActivity.class);
			startActivity(i2);
		}
		
	};
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

}
