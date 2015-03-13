package com.project.service;

import java.util.Collection;

import javax.ejb.EJB;

import com.project.dao.FailureClassDAO;
import com.project.entities.FailureClass;


public class FailureClassServiceEJB implements FailureClassService{
	
	@EJB
	private FailureClassDAO dao;

	@Override
	public Collection<FailureClass> getAllFailureClasses() {
		return dao.getAllFailureClasses();
	}

}
