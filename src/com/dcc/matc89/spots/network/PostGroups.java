package com.dcc.matc89.spots.network;


import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.model.Group;
import com.dcc.matc89.spots.network.FetchGroups.OnGroupsReceiver;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PostGroups {
	
	public PostGroups() {
	}

	public void newGroup(OnGroupsReceiver receiver, Group group, long creatorId) {
		String params[] = {"name", group.getName(),
				"description", group.getDescription(),
				"sport", String.valueOf(group.getSport().getId()),
				"creator", String.valueOf(creatorId)};
		postGroup(receiver, params);
	}

	public void editGroup(OnGroupsReceiver receiver, Group group) {
		String params[] = {"id", String.valueOf(group.getId()),
				"name", group.getName(),
				"description", group.getDescription(),
				"sport", String.valueOf(group.getSport().getId())};
		postGroup(receiver, params);
	}

	private void postGroup(OnGroupsReceiver receiver, String[] params) {
		PostGroupsAsyncTask task = new PostGroupsAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
	
}
