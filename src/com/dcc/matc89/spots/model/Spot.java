package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Spot  implements Serializable{

	private static final long serialVersionUID = 1217102246152910571L;

	private long id;
	private String name, description;
	private List<Group> groups;
	private List<Sport> sports;
	private double latitude;
	private double longitude;
	
	public Spot(long id, String name, String description, List<Group> groups, List<Sport> sports, double lat, double lng) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.groups = groups;
		this.sports = sports;
		this.latitude = lat;
		this.longitude = lng;
	}
	
	public static Spot createFromJSONObject(JSONObject object) throws JSONException{
		
		return new Spot(
				object.getLong("id"), 
				object.getString("name"), 
				object.getString("description"), 
				null, // FIXME groups of spots
				null, // FIXME sports of spots
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
		return groups.size();
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


	public List<Group> getGroups() {
		return groups;
	}


	public void setGroups(List<Group> groups) {
		this.groups = groups;
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
