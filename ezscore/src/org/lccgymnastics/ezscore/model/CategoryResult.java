package org.lccgymnastics.ezscore.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryResult implements MSConstants {
	
	private Map<EventType, List<IndividualScore>> results;
	
	public CategoryResult() {
		this.results = new HashMap<EventType, List<IndividualScore>>();
	}
	public void addResult(EventType event, Competitor c) {
		if (!this.results.containsKey(event)) {
			this.results.put(event, new ArrayList<IndividualScore>());
		}
		List<IndividualScore> scores = this.results.get(event);
		scores.add(new IndividualScore(c.getNumber(), c.getName(), c.getTeam(), c.getScore(event), event, c.getCategory()==Category.VO));
	}
	
	public List<IndividualScore> getIndividualScores(EventType event) {
		return this.results.get(event);
	}
}
