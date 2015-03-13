package com.project.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-03-13T17:50:43.482+0000")
@StaticMetamodel(EventCause.class)
public class EventCause_ {
	public static volatile SingularAttribute<EventCause, Integer> id;
	public static volatile SingularAttribute<EventCause, Integer> causeCode;
	public static volatile SingularAttribute<EventCause, Integer> eventId;
	public static volatile SingularAttribute<EventCause, String> description;
}
