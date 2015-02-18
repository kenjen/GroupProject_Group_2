package com.project.service;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

@Local
public interface EventCauseService {
	public Collection getFailuresIdsByIMSI(Long imsi);

	public Collection getFailuresIds();
}
