package com.dcc.matc89.spots.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

import org.json.JSONObject;

import android.os.AsyncTask;

class PostLinkSpotGroupAsyncTask extends AsyncTask<String, Void, String> {

	private static final String URL = "http://matc89spots.appspot.com/api/linkgroupspot";

	private OnResultReceiver mReceiver;


	public PostLinkSpotGroupAsyncTask(OnResultReceiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected String doInBackground(String... params) {
		if(params != null && params.length % 2 == 1)
			throw new InvalidParameterException("Parameters should be in pairs. Key and value.");
		try {
			URL url = new URL(Utils.getUrl(URL, params));
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			InputStream inputStream = urlConnection.getInputStream();
			String data;
			try {
				data = Utils.convertInputStreamToString(inputStream);
			} finally {
				if(inputStream != null)
					inputStream.close();
				urlConnection.disconnect();
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		boolean okay = false;
		try{
			okay = new JSONObject(result).getString("result").equalsIgnoreCase("OK");
		} catch (Exception e) {
		}
		mReceiver.onResult(okay, result);
	}


}
