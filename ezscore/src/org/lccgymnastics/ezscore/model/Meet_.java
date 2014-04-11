package org.lccgymnastics.ezscore.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-04-03T14:43:18.231-0700")
@StaticMetamodel(Meet.class)
public class Meet_ {
	public static volatile SingularAttribute<Meet, Integer> ID;
	public static volatile SingularAttribute<Meet, String> description;
	public static volatile SingularAttribute<Meet, Date> date;
	public static volatile MapAttribute<Meet, String, Competitor> competitors;
}
