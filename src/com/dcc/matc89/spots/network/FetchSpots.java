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
		fetchSpots(receiver, null);
	}

	public void getSpots(OnSpotsReceiver receiver, long fromGroup) {
		String params[] = {"fromGroup", String.valueOf(fromGroup)};
		fetchSpots(receiver, params);
	}

	public void getSpots(OnSpotsReceiver receiver, double latitude, double longitude, int limit, int offset) {
		String params[] = {"latitude", String.valueOf(latitude),
				"longitude", String.valueOf(longitude),
				"limit", String.valueOf(limit),
				"offset", String.valueOf(offset)};
		fetchSpots(receiver, params);
	}

	private void fetchSpots(OnSpotsReceiver receiver, String[] params) {
		if(receiver == null)
			return;
		FetchSpotsAsyncTask task = new FetchSpotsAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
	
	public interface OnSpotsReceiver{
		void onSpotsReceived(List<Spot> spots);
	}
}
