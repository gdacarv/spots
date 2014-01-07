package com.dcc.matc89.spots.network;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.model.Spot;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PostLinkSpotGroup {
	
	public void linkSpotGroup(Spot spot, Group group, OnResultReceiver receiver) {
		String params[] = {"spotId", String.valueOf(spot.getId()),
				"groupId", String.valueOf(group.getId()),
				"linkOrUnlink", "link"};
		postLinkSpotGroup(receiver, params);
	}

	private void postLinkSpotGroup(OnResultReceiver receiver, String[] params) {
		if(receiver == null)
			return;
		PostLinkSpotGroupAsyncTask task = new PostLinkSpotGroupAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
}
