package com.dcc.matc89.spots.model;

import java.io.Serializable;

public class Sport implements Serializable{

	private static final long serialVersionUID = 6573401669722205854L;

	private final String name;

	public Sport(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
