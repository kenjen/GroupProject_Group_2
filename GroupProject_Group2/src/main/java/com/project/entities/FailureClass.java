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
