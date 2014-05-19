package org.lccgymnastics.ezscore.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TeamResult implements MSConstants, Comparable<TeamResult> {
	private String team;
	private Collection<IndividualScore> teamScores;
	private Collection<IndividualScore> includedScores;
	private Double score;
	private static final int VARSITY_COUNT = 6;
	private static final int JV_COUNT = 4;
	
	public TeamResult(String team, EventType event, Collection<IndividualScore> teamScores, int minOptionalScores) {
		this.team = team;
		this.teamScores = teamScores;
		this.includedScores = new ArrayList<IndividualScore>();
		if (minOptionalScores>=0) {
			computeVarsityScore(event, minOptionalScores);
		} else {
			computeJVScore(event);
		}
	}
	
	private void computeJVScore(EventType ev) {
		this.score = 0.0; 
		if (this.teamScores==null) return;
		Map<EventType, List<IndividualScore>> eventScores = collateAndSortEventScores();
		if (ev == EventType.ALLAROUND) { // All around is treated as the overall team score. Compute differently
			for (EventType event : EventType.values()) {
				if (event == EventType.ALLAROUND) continue; // Exclude AA for team scores
				computeJVEventScore(eventScores, event);
			}
		} else { // Individual Event team score
			computeJVEventScore(eventScores, ev);
		}
	}

	private void computeVarsityScore(EventType ev, int minOptionalScores) {
		this.score = 0.0;
		if (this.teamScores==null) return;
		Map<EventType, List<IndividualScore>> eventScores = collateAndSortEventScores();
		if (ev == EventType.ALLAROUND) { // All around is treated as the overall team score. Compute differently
			for (EventType event : EventType.values()) {
				if (event == EventType.ALLAROUND) continue; // Exclude AA for team scores
				computeVarsityEventScore(eventScores, event, minOptionalScores);
			}
		} else { // Individual Event team score
			computeVarsityEventScore(eventScores, ev, minOptionalScores);
		}
	}

	private List<IndividualScore> computeJVEventScore(
			Map<EventType, List<IndividualScore>> eventScores, EventType event) {
		List<IndividualScore> sortedEventScores = eventScores.get(event);
		if (sortedEventScores!=null) {
			for (int i=0; i<Math.min(sortedEventScores.size(),JV_COUNT); i++) {
				IndividualScore iScore = sortedEventScores.get(i);
				if (iScore!=null) {
					this.score += iScore.getScore();
					includedScores.add(iScore);
				}
			}
		}
		return sortedEventScores;
	}

	private void computeVarsityEventScore(Map<EventType, List<IndividualScore>> eventScores, EventType event, int minOptionalScores) {
		List<IndividualScore> sortedEventScores = eventScores.get(event);
		if (sortedEventScores!=null) {
			int oCount = 0;
			Iterator<IndividualScore> it = sortedEventScores.iterator();
			// Ensure the first (minOptionalScores) scores are optional				
			while (it.hasNext() && oCount<minOptionalScores) {
				IndividualScore iScore = it.next();
				if (iScore.isOptional()) {
					this.score += iScore.getScore();
					includedScores.add(iScore.clone());
					it.remove();
					++oCount;
				}
			}
			// Add the remaining (VARSITY_COUNT-minOptionalScores) highest scores				
			for (int i=0; i<Math.min(sortedEventScores.size(),(VARSITY_COUNT-minOptionalScores)); i++) {
				IndividualScore iScore = sortedEventScores.get(i);
				if (iScore!=null) {
					this.score += iScore.getScore();
					includedScores.add(iScore);
				}
			}
		}
	}

	/***
	 * Collates the individual scores by EventType and sorts each list
	 * 
	 * @return Map<EventType, List<IndividualScore>> containing sorted scores by event
	 */
	private Map<EventType, List<IndividualScore>> collateAndSortEventScores() {
		Map<EventType,List<IndividualScore>> eventScores = new HashMap<EventType,List<IndividualScore>>();
		for (IndividualScore iScore : this.teamScores) {
			List<IndividualScore> sortedEventScores = eventScores.get(iScore.getEvent());
			if (sortedEventScores==null) {
				sortedEventScores = new ArrayList<IndividualScore>();
				eventScores.put(iScore.getEvent(), sortedEventScores);
			}
			sortedEventScores.add(iScore);
		}
		for (EventType event : EventType.values()) {
			List<IndividualScore> scores = eventScores.get(event);
			if (scores!=null) Collections.sort(scores);
		}
		return eventScores;
	}


	public String getTeam() {
		return team;
	}
	public Double getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return String.format("%30.30s %2.4f", this.team, this.score);
	}
	
	@Override
	public int compareTo(TeamResult o) {
		return o.score.compareTo(this.score);
	}
}
