package com.dcc.matc89.spots.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticDatabase {
	
	private static StaticDatabase singleton = new StaticDatabase();
	
	private List<Group> groups = new ArrayList<Group>();
	private List<Long> groupsId;
	private List<User> users;
	private List<Sport> sports;
	private List<Spot> spots;

	public StaticDatabase() {
		groupsId = Arrays.asList(Long.valueOf(1), Long.valueOf(2), Long.valueOf(3), Long.valueOf(4));
		users = Arrays.asList(
				new User("Jo�o", "Salvador - BA", "1203029403293", groups),
				new User("Jose", "Salvador - BA", "1203029403293", groups),
				new User("Joaquim", "Salvador - BA", "1203029403293", groups),
				new User("Jorge", "Salvador - BA", "1203029403293", groups));
		sports = Arrays.asList(
				new Sport(1, "Basquete"),
				new Sport(2, "Vôlei"),
				new Sport(3, "Parkour"));
		spots  = Arrays.asList(
				new Spot(1, "Est�dio de Esportes UFBA", "Um spot qualquer", groupsId, sports, -12.971111, -38.510833),
				new Spot(2, "Parque Foo", "Um spot qualquer", groupsId, sports, -12.971111, -38.510833),
				new Spot(3, "Pra�a Orl�stica", "Um spot qualquer", groupsId, sports, -12.971111, -38.510833),
				new Spot(4, "Lugar para praticar esportes", "Um spot qualquer", groupsId, sports, -12.971111, -38.510833));
		groups = Arrays.asList(
				new Group("Carcar�", "Um grupo qualquer", users, spots, sports.get(0)),
				new Group("Chacal", "Um grupo qualquer", users, spots, sports.get(0)),
				new Group("Cutia", "Um grupo qualquer", users, spots, sports.get(0)),
				new Group("Limite Radical", "Um grupo qualquer", users, spots, sports.get(0)));
		for (int i = 0; i < 4; i = i+1) {
			users.get(i).setGroups(groups);
			spots.get(i).setGroupsId(groupsId);
		}
		setSports(Arrays.asList(
				new Sport(2, "Vôlei"),
				new Sport(4, "Futebol"),
				new Sport(1, "Basquete"),
				new Sport(5, "Slackline"),
				new Sport(6, "Escalada"),
				new Sport(3, "Parkour"))
				);
	}
	
	public static StaticDatabase getSingleton(){
		return singleton;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Sport> getSports() {
		return sports;
	}

	public void setSports(List<Sport> sports) {
		this.sports = sports;
	}

	public List<Spot> getSpots() {
		return spots;
	}

	public void setSpots(List<Spot> spots) {
		this.spots = spots;
	}
	
	public void addGroup(Group group){
		this.groups.add(group);
	}
	
	public void addSpot(Spot spot){
		this.spots.add(spot);
	}
	
	public void addUser(User user){
		this.users.add(user);
	}
	
	public void addSport(Sport sport){
		this.sports.add(sport);
	}
	
	public Group getGroupByName(String name){
		int i = 0;
		while(i < this.groups.size()){
			if(this.groups.get(i).getName().equalsIgnoreCase(name)){
				return this.groups.get(i);
			} else {
				i = i+1;
			}
		}
		return null;
	}
	
	public User getUserByFId (String facebookId) {
		int i = 0;
		while(i < this.users.size()){
			if (this.users.get(i).getFacebookId().equalsIgnoreCase(facebookId)) {
				return this.users.get(i);
			} else {
				i = i+1;
			}
		}
		return null;
	}
	
	public Sport getSportByName (String name) {
		int i = 0;
		while (i < this.sports.size()){
			if (this.sports.get(i).getName().equalsIgnoreCase(name)) {
				return this.sports.get(i);
			} else {
				i = i+1;
			}
		}
		return null;
	}
	
	public Spot getSpotByName (String name) {
		int i = 0;
		while (i < this.spots.size()){
			if (this.spots.get(i).getName().equalsIgnoreCase(name)){
				return this.spots.get(i);
			} else {
				i = i+1;
			}
		}
		return null;
	}
	
}
