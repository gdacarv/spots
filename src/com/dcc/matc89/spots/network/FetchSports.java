package com.dcc.matc89.spots.network;

import java.util.List;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.model.Sport;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FetchSports {
	
	public FetchSports() {
	}

	public void getSports(OnSportsReceiver receiver) {
		fetchSports(receiver, null);
	}

	private void fetchSports(OnSportsReceiver receiver, String[] params) {
		if(receiver == null)
			return;
		FetchSportsAsyncTask task = new FetchSportsAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
	
	public interface OnSportsReceiver{
		void onSportsReceived(List<Sport> sports);
	}
}
