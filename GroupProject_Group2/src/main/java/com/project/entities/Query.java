package com.project.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="queries")
public class Query implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="permission")
	private Integer permission;
	
	@Column(name="display_name")
	private String displayName;
	
	public Query(){
		
	}
	
	public Query(Integer id, Integer permission, String displayName){
		this.id = id;
		this.permission = permission;
		this.displayName = displayName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
