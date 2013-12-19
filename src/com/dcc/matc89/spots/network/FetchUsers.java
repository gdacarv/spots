package com.dcc.matc89.spots.network;

import java.util.List;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.model.User;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FetchUsers {
	
	public FetchUsers() {
	}

	public void getUsers(OnUsersReceiver receiver) {
		fetchUsers(receiver, null);
	}

	public void getUsers(OnUsersReceiver receiver, long fromGroup) {
		String params[] = {"fromGroup", String.valueOf(fromGroup)};
		fetchUsers(receiver, params);
	}

	private void fetchUsers(OnUsersReceiver receiver, String[] params) {
		if(receiver == null)
			return;
		FetchUsersAsyncTask task = new FetchUsersAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
	
	public interface OnUsersReceiver{
		void onUsersReceived(List<User> users);
	}
}
