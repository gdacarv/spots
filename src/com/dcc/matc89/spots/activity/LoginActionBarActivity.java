package com.dcc.matc89.spots.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.User;
import com.dcc.matc89.spots.social.LoginFragment;
import com.dcc.matc89.spots.social.OnUserLoginListener;

public abstract class LoginActionBarActivity extends ActionBarActivity implements OnUserLoginListener {

	private View mContent;

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(R.layout.activity_login_actionbar);
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		LoginFragment login = (LoginFragment) supportFragmentManager.findFragmentById(R.id.frag_login);
		login.setOnUserLoginListener(this);
		ViewGroup root = (ViewGroup) findViewById(R.id.layout_root);
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContent = inflater.inflate(layoutResID, root, false);
		mContent.setVisibility(View.INVISIBLE);
		root.addView(mContent);
	}
	
	@Override
	public void onUserLoggedIn(User user) {
		if(user != null){
			FragmentManager supportFragmentManager = getSupportFragmentManager();
			LoginFragment login = (LoginFragment) supportFragmentManager.findFragmentById(R.id.frag_login);
			supportFragmentManager.beginTransaction().remove(login).commitAllowingStateLoss();
			login.hide();
			mContent.setVisibility(View.VISIBLE);
			onLoggedIn(user);
		}
	}

	protected abstract void onLoggedIn(User user);
}
