package com.dcc.matc89.spots.network;

import java.util.List;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.model.Spot;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FetchSpots {
	
	public FetchSpots() {
	}

	public void getSpots(OnSpotsReceiver receiver) {
		if(receiver == null)
			return;
		FetchSpotsAsyncTask task = new FetchSpotsAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			task.execute();
	}
	
	public interface OnSpotsReceiver{
		void onSpotsReceived(List<Spot> spots);
	}

	/*public void update(List<Video> favorites, OnSpotsReceiver onUpdateFinished) {
		FetchSpotsAsyncTask task = new FetchSpotsAsyncTask(favorites, onUpdateFinished, mVideoFactory);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			task.execute();
	}

	public Video getLatestVideo(CharSequence user) {
		try {
			URL url = new URL(String.format(YouTubeUserAsyncTask.URL, user.toString(), 1, 1));
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			try {
				String data = Utils.convertInputStreamToString(inputStream);
				JSONArray items = new JSONObject(data).getJSONObject("data").getJSONArray("items");
				List<Video> videos = mVideoFactory.createVideos(items);
				return videos.get(0);
			} finally {
				if(inputStream != null)
					inputStream.close();
				urlConnection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
