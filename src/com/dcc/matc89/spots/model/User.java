package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{

	private static final long serialVersionUID = 3741969855647821691L;
	
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
}
