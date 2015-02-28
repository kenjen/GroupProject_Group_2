package com.project.dao;

import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.EventCause;


public interface EventCauseDAO {
	Collection getFailuresIdsByIMSI(Long imsi);

	Collection getFailures();

	Collection getAllEventCause();
}
