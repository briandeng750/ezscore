package org.lccgymnastics.ezscore.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TeamResults implements MSConstants, JSONObject {

	private Map<Category, Map<EventType,Set<TeamResult>>> teamResults;
	private int minOptionalScores = 2;
	
	public TeamResults(Meet m, int minOptionalScores) {
		this.minOptionalScores = minOptionalScores;
		this.teamResults = new HashMap<Category,Map<EventType,Set<TeamResult>>>();
		Map<String, Map<Category, Collection<IndividualScore>>> allResults = 
				new HashMap<String, Map<Category, Collection<IndividualScore>>>();
		IndividualResults ir = m.getIndividualResults();
		for (EventType event : EventType.values()) {
			for (Category category : Category.values()) {
				Collection<IndividualScore> vScores = ir.getResults(category, event);
				if (vScores==null) continue;
				for (IndividualScore individualScore : vScores) {
					if (individualScore.getScore()==null) continue;
					String team = individualScore.getTeam();
					Map<Category, Collection<IndividualScore>> teamResults = allResults.get(team);
					if (teamResults==null) {
						teamResults = new HashMap<Category, Collection<IndividualScore>>();
						allResults.put(team, teamResults);
					}
					Collection<IndividualScore> teamCatResults = teamResults.get(category);
					if (teamCatResults == null) {
						teamCatResults = new ArrayList<IndividualScore>();
						teamResults.put(category, teamCatResults);
					}
					teamCatResults.add(individualScore);
				}
			}
		}
		for (String team : allResults.keySet()) {
			Map<Category, Collection<IndividualScore>> teamScores = allResults.get(team);
			Collection<IndividualScore> jvScores = teamScores.get(Category.JV);
			if (jvScores!=null && !jvScores.isEmpty()) {
				Map<EventType,Set<TeamResult>> jvEventResults = teamResults.get(Category.JV);
				Set<TeamResult> jvTeamResults;
				if (jvEventResults==null) {
					jvEventResults = new HashMap<EventType,Set<TeamResult>>();
					for (EventType event: EventType.values()) {
						jvEventResults.put(event, new TreeSet<TeamResult>());
					}
					teamResults.put(Category.JV, jvEventResults);
				}
				
				for (EventType event: EventType.values()) {
					jvTeamResults = teamResults.get(Category.JV).get(event);
					jvTeamResults.add(new TeamResult(team, event, jvScores, -1));
				}
			}

			Map<EventType,Set<TeamResult>> varsityEventResults = teamResults.get(Category.VARSITY);
			if (varsityEventResults==null) {
				varsityEventResults = new HashMap<EventType,Set<TeamResult>>();
				for (EventType event: EventType.values()) {
					varsityEventResults.put(event, new TreeSet<TeamResult>());
				}
				teamResults.put(Category.VARSITY, varsityEventResults);
			}
			Set<TeamResult> varsityResults;
			Collection<IndividualScore> varsityScores = new ArrayList<IndividualScore>();
			Collection<IndividualScore> voScores = teamScores.get(Category.VO);
			Collection<IndividualScore> vcScores = teamScores.get(Category.VC);
			if (voScores!=null) varsityScores.addAll(voScores);
			if (vcScores!=null) varsityScores.addAll(vcScores);
			for (EventType event: EventType.values()) {
				varsityResults = teamResults.get(Category.VARSITY).get(event);
				varsityResults.add(new TeamResult(team, event, varsityScores, this.minOptionalScores));
			}
		}
	}
	
	public Collection<TeamResult> getJVResults(EventType event) {
		Map<EventType, Set<TeamResult>> catResult = this.teamResults.get(Category.JV);
		if (catResult!=null) return catResult.get(event);
		else return null;
	}

	public Collection<TeamResult> getVarsityResults(EventType event) {
		Map<EventType, Set<TeamResult>> catResult = this.teamResults.get(Category.VARSITY);
		if (catResult!=null) return catResult.get(event);
		else return null;
	}
	
	@Override
	public String toJSON() {
		return Globals.GSON.toJson(this);
	}
}
