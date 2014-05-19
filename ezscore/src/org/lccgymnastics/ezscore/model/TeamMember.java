package org.lccgymnastics.ezscore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Query;

import org.lccgymnastics.ezscore.model.MSConstants.Category;

@Entity (name = "TEAM_MEMBER")
public class TeamMember {
	
	@Id
	@Column (name = "number")
	private String number;
	
	@Column (name = "name")
	private String name;
	
	@Column (name = "category")
	@Enumerated(EnumType.STRING)
	private Category category;

	protected TeamMember() {}
	public TeamMember(String number, String name, Category category) {
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
		return this.number.equals(((TeamMember)obj).number);
	}

	public static TeamMember getCompetitor(EntityManager em, String number) {
		Query q = em.createQuery("select t from HTCOMPETITOR t where t.number = '"+number+"'");
		return (TeamMember)q.getSingleResult();
	}
	
	@Override
	public String toString() {
		return "Number("+this.number+") Name("+this.name+") Category("+this.category.toString()+")";
	}
}
