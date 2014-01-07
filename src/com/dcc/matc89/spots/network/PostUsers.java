package com.dcc.matc89.spots.network;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.network.FetchUsers.OnUsersReceiver;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PostUsers {
	
	public void postUsers(OnUsersReceiver receiver, String name, String location, String facebookId, String googleplusId) {
		int qtParams = 0;
		if(name != null) qtParams++;
		if(location != null) qtParams++;
		if(facebookId != null) qtParams++;
		if(googleplusId != null) qtParams++;
		String params[] = new String[qtParams*2];
		int i = 0;
		if(name != null){
			params[i++] = "name";
			params[i++] = name;
		}
		if(location != null){
			params[i++] = "location";
			params[i++] = location;
		}
		if(facebookId != null){
			params[i++] = "facebookId";
			params[i++] = facebookId;
		}
		if(googleplusId != null){
			params[i++] = "googleplusId";
			params[i++] = googleplusId;
		}
		postUsers(receiver, params);
	}

	private void postUsers(OnUsersReceiver receiver, String[] params) {
		if(receiver == null)
			return;
		PostUsersAsyncTask task = new PostUsersAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
}
