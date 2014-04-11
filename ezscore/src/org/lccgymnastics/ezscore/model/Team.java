package org.lccgymnastics.ezscore.model;


public class Team {
	private String name;

	protected Team() {}
	public Team(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
