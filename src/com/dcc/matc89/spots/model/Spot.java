package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.List;
import com.google.android.gms.maps.model.LatLng;

public class Spot  implements Serializable{

	private static final long serialVersionUID = 1217102246152910571L;

	private String name, description;
	private List<Group> groups;
	private List<Sport> sports;
	private double latitude;
	private double longitude;
	
	public Spot(String name, String description, List<Group> groups, List<Sport> sports, double lat, double lng) {
		this.name = name;
		this.description = description;
		this.groups = groups;
		this.sports = sports;
		this.latitude = lat;
		this.longitude = lng;
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


	public void setSports(List<Sport> sports) {
		this.sports = sports;
	}
	
	@Override
	public String toString() { // Need to override this to use ArrayAdapter.
		return getName();
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
	
}
