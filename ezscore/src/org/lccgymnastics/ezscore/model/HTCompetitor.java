package org.lccgymnastics.ezscore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Query;

import org.lccgymnastics.ezscore.model.MSConstants.Category;

@Entity (name = "HTCOMPETITOR")
public class HTCompetitor {
	
	@Id
	@Column (name = "number")
	private String number;
	
	@Column (name = "name")
	private String name;
	
	@Column (name = "category")
	@Enumerated(EnumType.STRING)
	private Category category;

	protected HTCompetitor() {}
	public HTCompetitor(String number, String name, Category category) {
		this.number = number;
		this.name = name;
		this.category = category;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.number.equals(((HTCompetitor)obj).number);
	}

	@SuppressWarnings("unchecked")
	public static List<HTCompetitor> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT h from HTCOMPETITOR h");
		return (List<HTCompetitor>)q.getResultList();
	}
	
	public static HTCompetitor getCompetitor(EntityManager em, String number) {
		Query q = em.createQuery("select t from HTCOMPETITOR t where t.number = '"+number+"'");
		return (HTCompetitor)q.getSingleResult();
	}
}
