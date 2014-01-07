package com.dcc.matc89.spots.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.dcc.matc89.spots.model.User;
import com.dcc.matc89.spots.network.FetchUsers.OnUsersReceiver;

class PostUsersAsyncTask extends AsyncTask<String, Void, List<User>> {

	private static final String URL = "http://matc89spots.appspot.com/api/edit_user";

	private OnUsersReceiver mReceiver;
	private List<User> mUsers;


	public PostUsersAsyncTask(OnUsersReceiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected List<User> doInBackground(String... params) {
		if(params != null && params.length % 2 == 1)
			throw new InvalidParameterException("Parameters should be in pairs. Key and value.");
		mUsers = new ArrayList<User>(1);
		try {
			URL url = new URL(Utils.getUrl(URL, params));
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			InputStream inputStream = urlConnection.getInputStream();
			try {
				String data = Utils.convertInputStreamToString(inputStream);
				mUsers.add(User.createFromJSONObject(new JSONObject(data)));
			} finally {
				if(inputStream != null)
					inputStream.close();
				urlConnection.disconnect();
			}
			return mUsers;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mUsers;
	}

	@Override
	protected void onPostExecute(List<User> result) {
		super.onPostExecute(result);
		mReceiver.onUsersReceived(result);
	}


}
