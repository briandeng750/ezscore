package org.lccgymnastics.ezscore.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Query;

@Entity (name = "TEAM")
public class Team implements JSONObject{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	@Column(name = "name",unique=true)
	private String name;

	@OneToMany (cascade=CascadeType.ALL)
	@MapKey(name = "number")
	private List<TeamMember> members;

	protected Team() {}
	public Team(String name) {
		this.name = name;
		this.members = new ArrayList<TeamMember>();
	}
	
	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TeamMember> getMembers() {
		return members;
	}
	public TeamMember getMember(String number) {
		for (TeamMember comp : members) {
			if (number.equals(comp.getNumber())) {
				return comp;
			}
		}
		return null;
	}
	public TeamMember removeMember(String number) {
		for (Iterator<TeamMember> i = members.iterator(); i.hasNext();) {
			TeamMember member = i.next();
			if (number.equals(member.getNumber())) {
				i.remove();
				return member;
			}
		}
		return null;
	}
	public void addMember(TeamMember member) {
		this.members.add(member);
	}
	public void setMembers(List<TeamMember> members) {
		this.members.clear();
		this.members.addAll(members);
	}
	
	public static Team getTeam(EntityManager em, String name) {
		Query q = em.createQuery("select t from TEAM t where t.name = '"+name+"'");
		return (Team)q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public static List<String> getTeams(EntityManager em) {
		Query q = em.createQuery("SELECT m from TEAM m");
		List<Team> teams = (List<Team>)q.getResultList();
		List<String> retList = new ArrayList<String>();
		for (Team team : teams) {
			retList.add(team.toString());
		}
		return retList;
	}

	@SuppressWarnings("unchecked")
	public static List<Team> getAllTeams(EntityManager em) {
		Query q = em.createQuery("SELECT t from TEAM t");
		return q.getResultList();
		
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toJSON() {
		return Globals.GSON.toJson(this);
	}

}
