package com.dcc.matc89.spots.network;

import java.util.List;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.model.Group;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FetchGroups {
	
	public FetchGroups() {
	}

	public void getGroups(OnGroupsReceiver receiver) {
		fetchGroups(receiver, null);
	}

	public void getGroupsFromUser(OnGroupsReceiver receiver, long fromUser) {
		String params[] = {"fromUser", String.valueOf(fromUser)};
		fetchGroups(receiver, params);
	}

	public void getGroupsFromSpot(OnGroupsReceiver receiver, long fromSpot) {
		String params[] = {"fromUser", String.valueOf(fromSpot)};
		fetchGroups(receiver, params);
	}

	private void fetchGroups(OnGroupsReceiver receiver, String[] params) {
		if(receiver == null)
			return;
		FetchGroupsAsyncTask task = new FetchGroupsAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
	
	public interface OnGroupsReceiver{
		void onGroupsReceived(List<Group> groups);
	}
}
