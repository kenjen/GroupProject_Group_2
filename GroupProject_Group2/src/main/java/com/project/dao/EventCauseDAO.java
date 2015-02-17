package com.project.dao;

import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.EventCause;


@Local
public interface EventCauseDAO {
	public Collection getFailuresIdsByIMSI(String imsi);
}
