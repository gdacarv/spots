package com.dcc.matc89.spots.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Group implements Serializable{

	private static final long serialVersionUID = -6805623066594276610L;
	
	private long id;
	private String name, description;
	private List<Long> users;
	private Sport sport;
	private List<Long> spots;
	
	
	public Group(long id, String name, String description, List<Long> users, List<Long> spots, Sport sport) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
		this.spots = spots;
		this.sport = sport;
	}

	public static Group createFromJSONObject(JSONObject object) throws JSONException{
		JSONArray usersArray = object.getJSONArray("users");
		List<Long> users = new ArrayList<Long>(usersArray.length());
		for(int i = 0; i < usersArray.length(); i++)
			users.add(usersArray.getLong(i));
		JSONArray spotsArray = object.getJSONArray("spots");
		List<Long> spots = new ArrayList<Long>(spotsArray.length());
		for(int i = 0; i < spotsArray.length(); i++)
			spots.add(spotsArray.getLong(i));
		Sport sport = Sport.createFromJSONObject(object.getJSONObject("sport"));
		
		return new Group(
				object.getLong("id"), 
				object.getString("name"), 
				object.getString("description"), 
				users,
				spots,
				sport);
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
	
	public int getMembersCount(){
		return users.size();
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<Long> getUsers() {
		return users;
	}


	public void setUsers(List<Long> users) {
		this.users = users;
	}


	public Sport getSport() {
		return sport;
	}


	public void setSport(Sport sport) {
		this.sport = sport;
	}


	public List<Long> getSpots() {
		return spots;
	}


	public void setSpots(List<Long> spots) {
		this.spots = spots;
	}

	public int getSpotsCount(){
		return spots.size();
	}
	
	@Override
	public String toString() { // Need to override this to use ArrayAdapter.
		return getName();
	}
	
	public boolean containsUser(User user){
		Long userId = user.getId();
		for(Long id : users)
			if(id.equals(userId))
				return true;
		return false;
	}
	
	public boolean containsSpot(Spot spot){
		Long spotId = spot.getId();
		for(Long id : spots)
			if(id.equals(spotId))
				return true;
		return false;
	}
}
