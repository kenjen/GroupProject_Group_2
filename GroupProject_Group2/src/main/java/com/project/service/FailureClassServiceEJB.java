package com.project.service;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.dao.FailureClassDAO;
import com.project.entities.FailureClass;

@Local
@Stateless
public class FailureClassServiceEJB implements FailureClassService{
	
	@EJB
	private FailureClassDAO dao;

	@Override
	public Collection<FailureClass> getAllFailureClasses() {
		return dao.getAllFailureClasses();
	}

}
