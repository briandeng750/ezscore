package org.lccgymnastics.ezscore.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndividualResults implements MSConstants, JSONObject {

	private Map<Category, CategoryResult> results;
	
	public IndividualResults(Collection<Competitor> competitors) {
		results = new HashMap<Category, CategoryResult>();
		for (Competitor c  : competitors) {
			this.addResult(c);
		}
		sortResults();
	}
	private void sortResults() {
		for (Category cat: Category.values()) {
			CategoryResult cr = results.get(cat);
			if (cr==null) continue;
			for (EventType event: EventType.values()) {
				List<IndividualScore>scores = cr.getIndividualScores(event);
				if (scores!=null) Collections.sort(scores);
			}
		}
	}
	
	public void addResult(Competitor c) {
		c.updateAAScore();
		if (!results.containsKey(c.getCategory())) {
			results.put(c.getCategory(), new CategoryResult());
		}
		CategoryResult cr = results.get(c.getCategory());
		Double s = c.getScore(EventType.VAULT);
		if (s!= null && s>0.0) {
			cr.addResult(EventType.VAULT, c);
		}
		s = c.getScore(EventType.BARS);
		if (s!= null && s>0.0) {
			cr.addResult(EventType.BARS, c);
		}
		s = c.getScore(EventType.BEAM);
		if (s!= null && s>0.0) {
			cr.addResult(EventType.BEAM, c);
		}
		s = c.getScore(EventType.FLOOR);
		if (s!= null && s>0.0) {
			cr.addResult(EventType.FLOOR, c);
		}
		s = c.getScore(EventType.ALLAROUND);
		if (s!= null && s>0.0) {
			cr.addResult(EventType.ALLAROUND, c);
		}
	}
	
	public Collection<IndividualScore> getResults(Category category, EventType event) {
		CategoryResult cr = this.results.get(category);
		if (cr!=null) return cr.getIndividualScores(event);
		return null;
	}
	
	@Override
	public String toJSON() {
		return Globals.GSON.toJson(this);
	}
}
