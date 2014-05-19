package org.lccgymnastics.ezscore.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.lccgymnastics.ezscore.model.MSConstants.Category;
import org.lccgymnastics.ezscore.model.MSConstants.EventType;

public class PrintResult implements JSONObject {

	private String header;
	private Collection<IndividualScore> scores;
	private Map<EventType,Collection<TeamResult>> teamResults;
	
	protected PrintResult() {}
	public PrintResult(Collection<IndividualScore> scores, String header) {
		this.scores = scores;
		this.header = header;
	}
	public PrintResult(Category cat, TeamResults results, String header) {
		this.header = header;
		this.scores = null;
		this.teamResults = new HashMap<MSConstants.EventType, Collection<TeamResult>>();
		if (cat == Category.JV) {
			for (EventType event : EventType.values()) {
				teamResults.put(event, results.getJVResults(event));				
			}
		} else if (cat == Category.VARSITY) {
			for (EventType event : EventType.values()) {
				teamResults.put(event, results.getVarsityResults(event));				
			}
		}
	}
	public String getHeader() {
		return header;
	}
	public Collection<IndividualScore> getScores() {
		return scores;
	}
	@Override
	public String toJSON() {
		return Globals.GSON.toJson(this);
	}
}
