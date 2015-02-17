package com.project.service;

import java.util.Collection;

import javax.ejb.Local;




@Local
public interface EventCauseService {
	public Collection getFailuresIdsByIMSI(String imsi);
}
