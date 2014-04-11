package org.lccgymnastics.ezscore.model;


public class CompetitorResults implements MSConstants {

	private EventType event;
	private double score;
	
	public EventType getEvent() {
		return event;
	}
	public void setEvent(EventType event) {
		this.event = event;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}
