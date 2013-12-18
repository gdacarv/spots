package com.dcc.matc89.spots.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.AsyncTask;

import com.dcc.matc89.spots.model.Sport;
import com.dcc.matc89.spots.network.FetchSports.OnSportsReceiver;

class FetchSportsAsyncTask extends AsyncTask<String, Void, List<Sport>> {

	private static final String URL = "http://matc89spots.appspot.com/api/sports";

	private OnSportsReceiver mReceiver;
	private List<Sport> mSports;


	public FetchSportsAsyncTask(OnSportsReceiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected List<Sport> doInBackground(String... params) {
		if(params != null && params.length % 2 == 1)
			throw new InvalidParameterException("Parameters should be in pairs. Key and value.");
		mSports = new ArrayList<Sport>();
		try {
			URL url = new URL(Utils.getUrl(URL, params));
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			try {
				String data = Utils.convertInputStreamToString(inputStream);
				JSONArray sports = new JSONArray(data);
				for(int i = 0; i < sports.length(); i++)
					mSports.add(Sport.createFromJSONObject(sports.getJSONObject(i)));
			} finally {
				if(inputStream != null)
					inputStream.close();
				urlConnection.disconnect();
			}
			return mSports;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mSports;
	}

	@Override
	protected void onPostExecute(List<Sport> result) {
		super.onPostExecute(result);
		mReceiver.onSportsReceived(result);
	}


}
