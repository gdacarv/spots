package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable{

	private static final long serialVersionUID = -6805623066594276610L;
	
	private String name, description;
	private List<User> users;
	private Sport sport;
	private List<Spot> spots;
	
	
	public Group(String name, String description, List<User> users, List<Spot> spots, Sport sport) {
		this.name = name;
		this.description = description;
		this.users = users;
		this.spots = spots;
		this.sport = sport;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public int getMembersCount(){
		return users.size();
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public Sport getSport() {
		return sport;
	}


	public void setSport(Sport sport) {
		this.sport = sport;
	}


	public List<Spot> getSpots() {
		return spots;
	}


	public void setSpots(List<Spot> spots) {
		this.spots = spots;
	}

	public int getSpotsCount(){
		return spots.size();
	}
	
	@Override
	public String toString() { // Need to override this to use ArrayAdapter.
		return getName();
	}
}
