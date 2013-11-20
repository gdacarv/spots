package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	private static final long serialVersionUID = 3741969855647821691L;
	
	private String name, location, facebookId;
	
	private List<Group> groups;

	public User(String name, String location, String facebookId, List<Group> groups) {
		this.name = name;
		this.location = location;
		this.facebookId = facebookId;
		this.groups = groups;
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

	public List<Group> getGroups() {
		return groups;
	}

	@Override
	public String toString() {
		return getName();
	}
}
