package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Spot  implements Serializable{

	private static final long serialVersionUID = 1217102246152910571L;

	private long id;
	private String name, description;
	private List<Long> groupsId;
	private List<Sport> sports;
	private double latitude;
	private double longitude;
	
	public Spot(long id, String name, String description, List<Long> groups, List<Sport> sports, double lat, double lng) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.groupsId = groups;
		this.sports = sports;
		this.latitude = lat;
		this.longitude = lng;
	}
	
	public static Spot createFromJSONObject(JSONObject object) throws JSONException{
		JSONArray groupsArray = object.getJSONArray("groups");
		List<Long> groups = new ArrayList<Long>(groupsArray.length());
		for(int i = 0; i < groupsArray.length(); i++)
			groups.add(groupsArray.getLong(i));
		JSONArray sportsArray = object.getJSONArray("sports");
		List<Sport> sports = new ArrayList<Sport>(sportsArray.length());
		for(int i = 0; i < sportsArray.length(); i++)
			sports.add(Sport.createFromJSONObject(sportsArray.getJSONObject(i)));
		
		return new Spot(
				object.getLong("id"), 
				object.getString("name"), 
				object.getString("description"), 
				groups,
				sports,
				object.getDouble("latitude"), 
				object.getDouble("longitude"));
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGroupsCount() {
		return groupsId.size();
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getGroupsIds() {
		return groupsId;
	}

	public void setGroupsId(List<Long> groupsId) {
		this.groupsId = groupsId;
	}

	public List<Sport> getSports() {
		return sports;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setSports(List<Sport> sports) {
		this.sports = sports;
	}
	
	public LatLng getLatLng(){
		return new LatLng (this.latitude,this.longitude);
	}
	
	@Override
	public String toString() { 
		return getName();
	}
	
}
