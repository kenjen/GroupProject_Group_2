package com.project.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*
 @NamedQueries(
 {@NamedQuery
 (name = "findEventCauseByIMSI", 
 query = "select b.eventId, b.causeCode from BaseData b where b.imsi = :IMSI "),
 */
@NamedQueries({
		@NamedQuery(name = "findEventCauseByIMSI", query = "SELECT b.eventId, b.causeCode from BaseData a, EventCause b"
				+ " where a.imsi = :IMSI and b.id = a.eventCauseFK.id"),
		@NamedQuery(name = "findEventCause", query = "select e from  EventCause e"),
		@NamedQuery(name = "countUniqueEventCauseByModel", query = "SELECT e.eventId, e.causeCode, count(b)  "
				+ "FROM EventCause e, BaseData b, UE ue where e.id = b.eventCauseFK.id and ue.id = b.ueFK.id and "
				+ "ue.marketingName = :phoneModel group by b.eventCauseFK"),
		@NamedQuery(name = "FailureClass.getImsiByCauseClass", query = "select b.imsi, f.description from FailureClass f, BaseData "
						+ "b where f.id = b.failureClassFK.id and f.failureClass =:failureClass"),

		@NamedQuery(name = "EventCause.getAllEventCause", query = "select e from EventCause e"),
		})

@Entity
@Table(name = "Event_Cause")
public class EventCause implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	// add attributes for all the remaining properties
	@Column(name = "cause_Code")
	private Integer causeCode;

	@Column(name = "event_Id")
	private Integer eventId;

	@Column(name = "description")
	private String description;
	
	public EventCause() {

	}

	public EventCause(Integer causeCode, Integer eventId, String description) {
		super();
		this.causeCode = causeCode;
		this.eventId = eventId;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
