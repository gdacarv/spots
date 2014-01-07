package com.dcc.matc89.spots.social;

import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcc.matc89.spots.R;
import com.dcc.matc89.spots.model.User;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;

public class LoginFragment extends FacebookFragment {

	public static final String TAG = "LoginFragment";
	private View mLoginLayout, mRefreshProgressBar;
	private OnUserLoginListener mOnUserLoginListener;
	private View mRoot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.fragment_login, container, false);
		mRefreshProgressBar = mRoot.findViewById(R.id.login_pgs);
		mLoginLayout = mRoot.findViewById(R.id.login_layout);
		LoginButton authButton = (LoginButton) mRoot.findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("user_location"));
		authButton.setFragment(this);
		updateLayoutVisibility(isLoggedIn(Session.getActiveSession()));
		return mRoot;
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		super.call(session, state, exception);
		boolean loggedIn = isLoggedIn(session);
		updateLayoutVisibility(loggedIn);
		FragmentActivity activity = getActivity();
		if(loggedIn && activity != null){
			User user = User.getCurrentUser(activity);
			if(user != null && mOnUserLoginListener != null)
				mOnUserLoginListener.onUserLoggedIn(user);
		}
	}

	public void setOnUserLoginListener(OnUserLoginListener listener){
		mOnUserLoginListener = listener;
	}

	private boolean isLoggedIn(Session session) {
		return session != null && session.isOpened();
	}

	private void updateLayoutVisibility(boolean loggedIn) {
		mLoginLayout.setVisibility(loggedIn ? View.INVISIBLE : View.VISIBLE);
		mRefreshProgressBar.setVisibility(loggedIn ? View.VISIBLE : View.INVISIBLE);
	}

	public void hide() {
		mRoot.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onUserLoggedIn(User user) {
		if(user != null && mOnUserLoginListener != null)
			mOnUserLoginListener.onUserLoggedIn(user);
	}
}
