package com.dcc.matc89.spots.activity;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.Sport;

public class GroupEditActivity extends ActionBarActivity {
	
	//private EditText mName, mDescription;
	private Spinner mSport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
		setupViews();
	}

	private void setupViews() {
		mSport = (Spinner) findViewById(R.id.spn_editgroup_sport);
		
		List<Sport> sports = getAllSports();
		SpinnerAdapter adapter = new ArrayAdapter<Sport>(this, android.R.layout.simple_spinner_item, sports);
		mSport.setAdapter(adapter);
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
			// TODO Send new group OR update group to WEB API
			setResult(RESULT_OK);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private List<Sport> getAllSports() { // This method can't perform long running operations such as network requests.
		// TODO Get real sports. 
		
		return Arrays.asList(
				new Sport("Vôlei"),
				new Sport("Futebol"),
				new Sport("Baquete"),
				new Sport("Slackline"),
				new Sport("Escalada"),
				new Sport("Parkour")
				);
	}
}
