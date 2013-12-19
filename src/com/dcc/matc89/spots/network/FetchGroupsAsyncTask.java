package com.dcc.matc89.spots.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.AsyncTask;

import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.model.Spot;
import com.dcc.matc89.spots.network.FetchGroups.OnGroupsReceiver;
import com.dcc.matc89.spots.network.FetchSpots.OnSpotsReceiver;

class FetchGroupsAsyncTask extends AsyncTask<String, Void, List<Group>> {

	private static final String URL = "http://matc89spots.appspot.com/api/groups";

	private OnGroupsReceiver mReceiver;
	private List<Group> mGroups;


	public FetchGroupsAsyncTask(OnGroupsReceiver receiver) {
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
			InputStream inputStream = urlConnection.getInputStream();
			try {
				String data = Utils.convertInputStreamToString(inputStream);
				JSONArray groups = new JSONArray(data);
				for(int i = 0; i < groups.length(); i++)
					mGroups.add(Group.createFromJSONObject(groups.getJSONObject(i)));
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
