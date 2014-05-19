package org.lccgymnastics.ezscore.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "meet")
public class Meet implements MSConstants, JSONObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;

	@Column(name = "description")
	private String description;
	
	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@ElementCollection
	private Set<String> teams;

	@OneToMany (cascade=CascadeType.ALL)
	@MapKey(name = "number")
	private Map<String,Competitor> competitors;

	public int getID() {
		return ID;
	}
	
	protected Meet() {}
	public Meet(String description, Date date, String[] teams) {
		this.description = description;
		this.date = date;
		this.competitors = new HashMap<String,Competitor>(100);
		this.teams = new HashSet<String>(Arrays.asList(teams));
	}
	public IndividualResults getIndividualResults() {
		return new IndividualResults(competitors.values());
	}
	
	public TeamResults getTeamResults(int minOptionalScores) {
		return new TeamResults(this, minOptionalScores);
	}

	public void loadTeamMembers(Team team) {
		List<TeamMember> comps = team.getMembers();
		for (TeamMember htCompetitor : comps) {
			addCompetitor(new Competitor(htCompetitor.getNumber(), htCompetitor.getName(), team.getName(), htCompetitor.getCategory()));
		}
	}

	public void addCompetitor(Competitor c) {
		assert(this.teams.contains(c.getTeam()));
		this.competitors.put(c.getNumber(), c);
	}
	
	public void updateCompetitor(String number, String name, String team, Category category) {
		Competitor c = this.competitors.get(number);
		c.setCategory(category);
		c.setName(name);
		c.setTeam(team);
	}
	public void deleteCompetitor(String number) {
		this.competitors.remove(number);
	}
	public Competitor getCompetitor(String number) {
		return this.competitors.get(number);
	}

	public Collection<Competitor> getCompetitors() {
		return this.competitors.values();
	}

	public void addTeam(String t) {
		this.teams.add(t);
	}
	
	public Set<String> getTeams() {
		return this.teams;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toJSON() {
		return Globals.GSON.toJson(this);
	}
	
	@Override
	public String toString() {
		return "Meet: "+this.description + " On: " + this.date + "  ID("+ID+")";
	}

	public static Meet getMeet(EntityManager em, int id) {
		Meet m = em.find(Meet.class, id);
		return m;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Meet> getAllMeets(EntityManager em) {
		Query q = em.createQuery("SELECT m from meet m");
		return q.getResultList();
	}
	
}
