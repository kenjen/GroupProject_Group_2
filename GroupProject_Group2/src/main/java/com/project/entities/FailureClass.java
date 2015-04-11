package com.project.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
		@NamedQuery(name = "FailureClass.findAll", query = "select o from FailureClass o"),
		@NamedQuery(name = "FailureClass.findByID", query = "select o from FailureClass o where o.id=:id"),
		@NamedQuery(name = "FailureClass.specificFailuresForTopTenCombi", query = "SELECT f.failureClass, f.description, count(b.id) as numOfFailures "
				+ "FROM FailureClass f, BaseData b, MccMnc m "
				+ "WHERE f.id = b.failureClassFK.id "
				+ "AND b.mccMncFK.id = m.id "
				+ "AND m.country = :country "
				+ "AND m.operator = :operator "
				+ "AND b.cellId = :cellId "
				+ "AND b.date BETWEEN :startDate AND :endDate "
				+ "GROUP BY f.failureClass "
				+ "ORDER BY numOfFailures DESC")
		
})
@Entity
public class FailureClass implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer failureClass;
	private String description;

	public FailureClass() {

	}

	public FailureClass(Integer failureClass, String desc) {
		super();
		this.failureClass = failureClass;
		this.description = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((failureClass == null) ? 0 : failureClass.hashCode());
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
		FailureClass other = (FailureClass) obj;
		if (failureClass == null) {
			if (other.failureClass != null)
				return false;
		} else if (!failureClass.equals(other.failureClass))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(Integer failureClass) {
		this.failureClass = failureClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
