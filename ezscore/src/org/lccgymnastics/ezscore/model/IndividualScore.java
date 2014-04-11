package org.lccgymnastics.ezscore.model;


public class IndividualScore implements MSConstants, Comparable<IndividualScore>, Cloneable {
	
	private String number;
	private String name;
	private Double score;
	private String team;
	private EventType event;
	private boolean isOptional;
	
	public IndividualScore(String number, String name, String team, Double score, EventType event, boolean isOptional) {
		this.number = number;
		this.name = name;
		this.team = team;
		this.score = score;
		this.event = event;
		this.isOptional = isOptional;
	}

	public String getTeam() {
		return team;
	}
	
	public Double getScore() {
		return score;
	}
	
	public boolean isOptional() {
		return isOptional;
	}
	
	public EventType getEvent() {
		return event;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.number.equals(((IndividualScore)obj).number);
	}
	
	@Override
	public int compareTo(IndividualScore o) {
		return o.score.compareTo(this.score);
	}

	@Override
	public String toString() {
		return String.format("%5s %30.30s %20.20s %2.3f", this.number, this.name, this.team, this.score);
	}
	
	@Override
	protected IndividualScore clone() {
		try {
			return (IndividualScore)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new UnsupportedOperationException(e);
		}
	}
}
