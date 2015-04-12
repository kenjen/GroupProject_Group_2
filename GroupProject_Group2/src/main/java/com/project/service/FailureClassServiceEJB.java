package com.project.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.dao.FailureClassDAO;
import com.project.entities.FailureClass;

@Local
@Stateless
public class FailureClassServiceEJB implements FailureClassService {

	@EJB
	private FailureClassDAO dao;

	@Override
	public Collection<FailureClass> getAllFailureClasses() {
		return dao.getAllFailureClasses();
	}

	@Override
	public List<Object[]> specificFailuresForTopTenCombi(String country,
			String operator, Integer cellId, Date startDate, Date endDate) {
		return dao.specificFailuresForTopTenCombi(country, operator, cellId,
				startDate, endDate);
	}

}
