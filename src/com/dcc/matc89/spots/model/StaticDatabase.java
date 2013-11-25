package com.dcc.matc89.spots.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticDatabase {
	
	private static StaticDatabase singleton = new StaticDatabase();
	
	private List<Group> groups = new ArrayList<Group>();
	private List<User> users;
	private List<Sport> sports;
	private List<Spot> spots;

	public StaticDatabase() {
		users = Arrays.asList(
				new User("João", "Salvador - BA", "1203029403293", groups),
				new User("Jose", "Salvador - BA", "1203029403293", groups),
				new User("Joaquim", "Salvador - BA", "1203029403293", groups),
				new User("Jorge", "Salvador - BA", "1203029403293", groups));
		sports = Arrays.asList(
				new Sport("Basquete"),
				new Sport("Vôlei"),
				new Sport("Parkour"));
		spots  = Arrays.asList(
				new Spot("Estádio de Esportes UFBA", "Um spot qualquer", groups, sports, -12.971111, -38.510833),
				new Spot("Parque Foo", "Um spot qualquer", groups, sports, -12.971111, -38.510833),
				new Spot("Praça Orlástica", "Um spot qualquer", groups, sports, -12.971111, -38.510833),
				new Spot("Lugar para praticar esportes", "Um spot qualquer", groups, sports, -12.971111, -38.510833));
		groups = Arrays.asList(
				new Group("Carcará", "Um grupo qualquer", users, spots, sports.get(0)),
				new Group("Chacal", "Um grupo qualquer", users, spots, sports.get(0)),
				new Group("Cutia", "Um grupo qualquer", users, spots, sports.get(0)),
				new Group("Limite Radical", "Um grupo qualquer", users, spots, sports.get(0)));
		for (int i = 0; i < 4; i = i+1) {
			users.get(i).setGroups(groups);
			spots.get(i).setGroups(groups);
		}
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
		
}
