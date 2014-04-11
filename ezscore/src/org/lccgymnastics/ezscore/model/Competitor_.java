package org.lccgymnastics.ezscore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.lccgymnastics.ezscore.model.MSConstants.Category;
import org.lccgymnastics.ezscore.model.MSConstants.EventType;

@Generated(value="Dali", date="2014-04-04T09:44:25.696-0700")
@StaticMetamodel(Competitor.class)
public class Competitor_ {
	public static volatile SingularAttribute<Competitor, Integer> ID;
	public static volatile SingularAttribute<Competitor, String> number;
	public static volatile SingularAttribute<Competitor, String> name;
	public static volatile SingularAttribute<Competitor, String> team;
	public static volatile SingularAttribute<Competitor, Category> category;
	public static volatile MapAttribute<Competitor, EventType, Double> competitorResults;
}
