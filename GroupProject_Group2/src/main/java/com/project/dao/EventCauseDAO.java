package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import com.project.entities.EventCause;


public interface EventCauseDAO {
	List<Object[]> getFailuresIdsByIMSI(Long imsi);

	Collection getFailures();

	Collection getAllEventCause();
}
