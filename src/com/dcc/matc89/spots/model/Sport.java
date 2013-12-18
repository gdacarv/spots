package com.dcc.matc89.spots.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Sport implements Serializable{

	private static final long serialVersionUID = 6573401669722205854L;

	private long id;
	private final String name;

	public Sport(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static Sport createFromJSONObject(JSONObject object) throws JSONException{
		return new Sport(
				object.getLong("id"), 
				object.getString("name"));
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
	
	@Override
	public String toString() { // Need to override this to use ArrayAdapter.
		return getName();
	}
}
