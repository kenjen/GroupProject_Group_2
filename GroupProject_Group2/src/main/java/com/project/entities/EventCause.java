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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((causeCode == null) ? 0 : causeCode.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventCause other = (EventCause) obj;
		if (causeCode == null) {
			if (other.causeCode != null)
				return false;
		} else if (!causeCode.equals(other.causeCode))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
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
