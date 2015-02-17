package com.project.entities;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
@NamedQueries({@NamedQuery
			(name = "findEventCauseByIMSI", 
			query = "select event_Id, cause_Code from base_data bd.IMSI = :IMSI ")})

@Entity
@Table( name = "Event_Cause")
public class EventCause implements Serializable{
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="id")
//	private Integer id;

	// add attributes for all the remaining properties
	@Column(name="cause_Code")
	private Integer causeCode;

	@Column(name="event_Id") 
	private Integer eventId;

	@Column(name="description")
	private String description;
	
	// create new object to store the two id's, but don't serialize it
//	@Transient
//	private EventCauseCombi eventCause = new EventCauseCombi(eventId, causeCode);
//	
	public EventCause() {

	}

	public EventCause(Integer causeCode, Integer eventId, String description) {
		super();
		this.causeCode = causeCode;
		this.eventId = eventId;
		this.description = description;
	}
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
//	public EventCauseCombi getEventCause() {
//		return eventCause;
//	}
//	public void setEventCause(EventCauseCombi eventCause) {
//		this.eventCause = eventCause;
//	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

