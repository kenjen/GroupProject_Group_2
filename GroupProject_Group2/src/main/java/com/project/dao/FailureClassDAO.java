package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.project.entities.FailureClass;

public interface FailureClassDAO {
	Collection<FailureClass> getAllFailureClasses();

	List<Object[]> specificFailuresForTopTenCombi(String country,
			String operator, Integer cellId, Date startDate, Date endDate);
}
