package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;

public class User implements Serializable{

	private static final long serialVersionUID = 3741969855647821691L;
	

	private static final String ID_KEY = "id_key";
	private static final String FACEBOOK_ID_KEY = "facebook_id_key";
	private static final String GOOGLEPLUS_ID_KEY = "googleplus_id_key";
	private static final String NAME_KEY = "name_key";
	private static final String LOCATION_KEY = "location_key";
	private static final String GROUPS_KEY = "groups_key";

	private static User mCurrentUser;

	
	private long id;
	private String name, location, facebookId, googleplusId;
	private List<Long> groups;

	public User(long id, String name, String location, String facebookId, String googleplusId, List<Long> groups) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.facebookId = facebookId;
		this.googleplusId = googleplusId;
		this.groups = groups;
	}
	
	public static User createFromJSONObject(JSONObject object) throws JSONException{
		JSONArray groupsArray = object.getJSONArray("groups");
		List<Long> groups = new ArrayList<Long>(groupsArray.length());
		for(int i = 0; i < groupsArray.length(); i++)
			groups.add(groupsArray.getLong(i));
		
		return new User(
				object.getLong("id"), 
				object.getString("name"), 
				object.getString("location"), 
				object.getString("facebookId"),
				object.getString("googleplusId"),
				groups);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getGoogleplusId() {
		return googleplusId;
	}

	public void setGoogleplusId(String googleplusId) {
		this.googleplusId = googleplusId;
	}

	public List<Long> getGroups() {
		return groups;
	}
	
	public void setGroups( List<Long> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	public static User getCurrentUser(Context context) {
		if(mCurrentUser == null) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			long id = prefs.getLong(ID_KEY, -1);
			if(id > 0){
				String[] groupsStr = prefs.getString(GROUPS_KEY, "").split(",");
				List<Long> groups = new ArrayList<Long>(groupsStr.length);
				for(String group : groupsStr)
					if(group != null && group.length() > 0)
						groups.add(Long.parseLong(group));
				mCurrentUser = new User(
						id,
						prefs.getString(NAME_KEY, null), 
						prefs.getString(LOCATION_KEY, null), 
						prefs.getString(FACEBOOK_ID_KEY, null), 
						prefs.getString(GOOGLEPLUS_ID_KEY, null),
						groups);
			}
		}
		return mCurrentUser;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static void setCurrentUser(Context context, User user) {
		mCurrentUser = user;
		Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		if(mCurrentUser != null) {
			StringBuilder groups = new StringBuilder(user.groups.size() > 0 ? user.groups.get(0).toString() : "");
			for(int i = 1; i < user.groups.size(); i++)
				groups.append(',').append(user.groups.get(i));
			editor.putLong(ID_KEY, user.id);
			editor.putString(NAME_KEY, user.name);
			editor.putString(LOCATION_KEY, user.location);
			editor.putString(FACEBOOK_ID_KEY, user.facebookId);
			editor.putString(GOOGLEPLUS_ID_KEY, user.googleplusId);
			editor.putString(GROUPS_KEY, groups.toString());
		} else {
			editor.putLong(ID_KEY, -1);
			editor.putString(NAME_KEY, null);
			editor.putString(LOCATION_KEY, null);
			editor.putString(FACEBOOK_ID_KEY, null);
			editor.putString(GOOGLEPLUS_ID_KEY, null);
			editor.putString(GROUPS_KEY, "");
		}
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
			editor.apply();
		else
			editor.commit();
	}
}
