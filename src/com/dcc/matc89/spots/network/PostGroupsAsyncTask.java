package com.dcc.matc89.spots.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.network.FetchGroups.OnGroupsReceiver;

class PostGroupsAsyncTask extends AsyncTask<String, Void, List<Group>> {

	private static final String URL = "http://matc89spots.appspot.com/api/edit_group";

	private OnGroupsReceiver mReceiver;
	private List<Group> mGroups;


	public PostGroupsAsyncTask(OnGroupsReceiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected List<Group> doInBackground(String... params) {
		if(params != null && params.length % 2 == 1)
			throw new InvalidParameterException("Parameters should be in pairs. Key and value.");
		mGroups = new ArrayList<Group>();
		try {
			URL url = new URL(Utils.getUrl(URL, params));
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			InputStream inputStream = urlConnection.getInputStream();
			try {
				String data = Utils.convertInputStreamToString(inputStream);
				mGroups.add(Group.createFromJSONObject(new JSONObject(data)));
			} finally {
				if(inputStream != null)
					inputStream.close();
				urlConnection.disconnect();
			}
			return mGroups;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mGroups;
	}

	@Override
	protected void onPostExecute(List<Group> result) {
		super.onPostExecute(result);
		mReceiver.onGroupsReceived(result);
	}


}
