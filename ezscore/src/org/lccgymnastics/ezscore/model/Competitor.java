package org.lccgymnastics.ezscore.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity (name = "competitor")
public class Competitor implements MSConstants, JSONObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	
	@Column (name = "number")
	private String number;
	
	@Column (name = "name")
	private String name;
	
	@Column(name = "team")
	private String team;
	
	@Column (name = "category")
	@Enumerated(EnumType.STRING)
	private Category category;

	@ElementCollection
	private Map<EventType, Double> competitorResults;
	
	protected Competitor() {}
	
	public Competitor(String number, String name, String teamName, Category category) {
		this.number = number;
		this.name = name;
		this.team = teamName;
		this.category = category;
		this.competitorResults = new HashMap<EventType, Double>();
	}

	public void updateScore(EventType event, Double score) {
		assert(event != EventType.ALLAROUND);
		if (score==null) {
			competitorResults.remove(event);
		} else {
			assert(score>0.0 && score<10.0);
			competitorResults.put(event, score);
		}
		updateAAScore();
	}

	public void updateAAScore() {
		Double s1 = competitorResults.get(EventType.VAULT);
		Double s2 = competitorResults.get(EventType.BARS);
		Double s3 = competitorResults.get(EventType.BEAM);
		Double s4 = competitorResults.get(EventType.FLOOR);
		if (s1!=null && s1 > 0.0 && s2 !=null && s2 > 0.0 && s3 != null && s3>0.0 && s4!=null && s4>0.0) { 
			competitorResults.put(EventType.ALLAROUND, s1+s2+s3+s4);
		} else {
			competitorResults.remove(EventType.ALLAROUND);
		}
	}
	public void clearScores() {
		competitorResults.clear();
	}
	public Double getScore(EventType event) {
		return competitorResults.get(event);
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toJSON() {
		return Globals.GSON.toJson(this);
	}
	@Override
	public boolean equals(Object obj) {
		return this.number.equals(((Competitor)obj).number);
	}
}
