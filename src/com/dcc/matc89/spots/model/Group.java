package com.dcc.matc89.spots.model;

import java.util.List;

public class Group {

	private String name;
	private List<User> users;
	
	
	public Group(String name, List<User> users) {
		super();
		this.name = name;
		this.users = users;
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

	@Override
	public String toString() { // Need to override this to use ArrayAdapter.
		return getName();
	}
}
