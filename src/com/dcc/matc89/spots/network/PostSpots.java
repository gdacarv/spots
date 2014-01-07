package com.dcc.matc89.spots.network;


import java.util.List;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.dcc.matc89.spots.model.Sport;
import com.dcc.matc89.spots.model.Spot;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PostSpots {
	
	public PostSpots() {
	}

	public void newSpot(OnSpotReceiver receiver, Spot spot, long creatorId) {
		String sports = "";
		List<Sport> spotSports = spot.getSports();
		for (int i = 0; i < spotSports.size()-1; i++) {
			sports = sports.concat(String.valueOf(spotSports.get(i).getId()));
			sports = sports.concat(",");
		}
		sports = sports.concat(String.valueOf(spotSports.get(spotSports.size()-1).getId()));
		String params[] = {"name", String.valueOf(spot.getName()),
				"description", String.valueOf(spot.getDescription()),
				"sports", String.valueOf(sports),
				"latitude", String.valueOf(spot.getLatitude()),
				"longitude", String.valueOf(spot.getLongitude()),
				"creator", String.valueOf(creatorId)};
		postSpot(receiver, params);
	}

	public void editSpot(OnSpotReceiver receiver, Spot spot) {
		String params[] = {"id", String.valueOf(spot.getId()),
				"name", String.valueOf(spot.getName()),
				"description", String.valueOf(spot.getDescription()),
				"sports", String.valueOf(spot.getSports().toArray().toString()),
				"latitude", String.valueOf(spot.getLatitude()),
				"longitude", String.valueOf(spot.getLongitude())};
		postSpot(receiver, params);
	}

	private void postSpot(OnSpotReceiver receiver, String[] params) {
		PostSpotsAsyncTask task = new PostSpotsAsyncTask(receiver);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			task.execute(params);
	}
	
	public interface OnSpotReceiver{
		void onSpotsReceived(Spot spot);
	}
}
