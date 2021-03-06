package com.dcc.matc89.spots.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.AsyncTask;

import com.dcc.matc89.spots.model.Spot;
import com.dcc.matc89.spots.network.FetchSpots.OnSpotsReceiver;

class FetchSpotsAsyncTask extends AsyncTask<String, Void, List<Spot>> {

	private static final String URL = "http://matc89spots.appspot.com/api/spots";

	private OnSpotsReceiver mReceiver;
	private List<Spot> mSpots;


	public FetchSpotsAsyncTask(OnSpotsReceiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected List<Spot> doInBackground(String... params) {
		if(params != null && params.length % 2 == 1)
			throw new InvalidParameterException("Parameters should be in pairs. Key and value.");
		mSpots = new ArrayList<Spot>();
		try {
			URL url = new URL(Utils.getUrl(URL, params));
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			try {
				String data = Utils.convertInputStreamToString(inputStream);
				JSONArray spots = new JSONArray(data);
				for(int i = 0; i < spots.length(); i++)
					mSpots.add(Spot.createFromJSONObject(spots.getJSONObject(i)));
			} finally {
				if(inputStream != null)
					inputStream.close();
				urlConnection.disconnect();
			}
			return mSpots;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mSpots;
	}

	@Override
	protected void onPostExecute(List<Spot> result) {
		super.onPostExecute(result);
		mReceiver.onSpotsReceived(result);
	}


}
