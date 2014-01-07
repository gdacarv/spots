package com.dcc.matc89.spots.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.dcc.matc89.spots.model.Spot;
import com.dcc.matc89.spots.network.PostSpots.OnSpotReceiver;

class PostSpotsAsyncTask extends AsyncTask<String, Void, Spot> {

	private static final String URL = "http://matc89spots.appspot.com/api/edit_spot";

	private OnSpotReceiver mReceiver;
	private Spot mSpot;


	public PostSpotsAsyncTask(OnSpotReceiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected Spot doInBackground(String... params) {
		if(params != null && params.length % 2 == 1)
			throw new InvalidParameterException("Parameters should be in pairs. Key and value.");
		try {
			URL url = new URL(Utils.getUrl(URL, params));
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			InputStream inputStream = urlConnection.getInputStream();
			try {
				String data = Utils.convertInputStreamToString(inputStream);
				mSpot = Spot.createFromJSONObject(new JSONObject(data));
			} finally {
				if(inputStream != null)
					inputStream.close();
				urlConnection.disconnect();
			}
			return mSpot;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mSpot;
	}

	@Override
	protected void onPostExecute(Spot result) {
		super.onPostExecute(result);
		if(mReceiver != null)
			mReceiver.onSpotsReceived(result);
	}


}
