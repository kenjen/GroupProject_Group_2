package com.project.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.project.entities.FailureClass;

@Local
public interface FailureClassService {
	Collection<FailureClass> getAllFailureClasses();

	List<Object[]> specificFailuresForTopTenCombi(String country,
			String operator, Integer cellId, Date startDate, Date endDate);
}
