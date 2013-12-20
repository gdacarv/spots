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
	private User mUser;
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
		
		mUser = (User) getIntent().getSerializableExtra(USER_KEY);
		
		// TODO Grab Profile pic from facebook or Google Plus
		mProfilePicture.setImageDrawable(getResources().getDrawable(R.drawable.ic_contact_picture_2));
		mName.setText(mUser.getName());
		mLocation.setText(mUser.getLocation());
		mGroupsAmount.setText(String.valueOf(mUser.getGroups().size()));
		mGroupsButton.setOnClickListener(onGroupClicked);
	}
	
	private OnClickListener onGroupClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(UserDetailActivity.this, GroupListActivity.class);
			i.putExtra(GroupListActivity.USER_KEY, mUser);
			startActivity(i);
		}
		
	};
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

}
