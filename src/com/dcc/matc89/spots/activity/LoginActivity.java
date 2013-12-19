package com.dcc.matc89.spots.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.dcc.matc89.spots.R;
import com.google.android.gms.common.*;
import com.google.android.gms.plus.PlusClient;

// Para que a ActionBar funcione em todas as versões é necessário estender ActionBarActivity ao invés de Activity
public class LoginActivity extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private ProgressDialog mConnectionProgressDialog;
    private PlusClient mPlusClient;
    private ConnectionResult mConnectionResult;
	
	
	public static class ErrorDialogFragment extends DialogFragment{
		private Dialog mDialog;
		
		public ErrorDialogFragment(){
			super();
			mDialog = null;
		}
		
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			return mDialog;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
			case CONNECTION_FAILURE_RESOLUTION_REQUEST :
				switch(resultCode) {
					case Activity.RESULT_OK :
						mConnectionResult = null;
			            mPlusClient.connect();
					break;
				}
			}
	}
	
//	private boolean servicesConnected() {
//		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//		ConnectionResult connectionResult = new ConnectionResult(resultCode, null);
//		if (resultCode == ConnectionResult.SUCCESS) {
//			Log.d("Service Connection", "Google Play services is available.");
//			return true;
//		} else {
//			int errorCode = connectionResult.getErrorCode();
//			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//			if (errorDialog != null) {
//				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
//				errorFragment.setDialog(errorDialog);
//			}
//		}
//		return false;
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mPlusClient = new PlusClient.Builder(this, this, this)
        	.setActions("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
        	.build();
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
		
		findViewById(R.id.sign_in_button).setOnClickListener(onClickLoginListener);

	}

	private OnClickListener onClickLoginListener = new OnClickListener() {
		
		@Override
	    public void onClick(View view) {
	        if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
	            if (mConnectionResult == null) {
	                mConnectionProgressDialog.show();
	            } else {
	                try {
	                    mConnectionResult.startResolutionForResult(LoginActivity.this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
	                } catch (SendIntentException e) {
	                    // Try connecting again.
	                    mConnectionResult = null;
	                    mPlusClient.connect();
	                }
	            }
	        }
	    }
	};
	
	@Override
	protected void onStart(){
		super.onStart();
		mPlusClient.connect();
	}


	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (mConnectionProgressDialog.isShowing()) {
	        // The user clicked the sign-in button already. Start to resolve
	        // connection errors. Wait until onConnected() to dismiss the
	        // connection dialog.
	        if (connectionResult.hasResolution()) {
	          try {
	                   connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
	           } catch (SendIntentException e) {
	                   mPlusClient.connect();
	           }
	        }
	      }
	      // Save the result and resolve the connection failure upon a user click.
	      mConnectionResult = connectionResult;
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mConnectionProgressDialog.dismiss();
		String accountName = mPlusClient.getAccountName();
        Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onStop(){
		mPlusClient.disconnect();
		super.onStop();
	}
}
