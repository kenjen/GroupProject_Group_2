package com.project.service;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

@Local
public interface EventCauseService {
	public List<Object[]> getFailuresIdsByIMSI(Long imsi);
	public List<Object[]> countUniqueEventCauseByModel(String phoneModel);
	public List<Object[]> getCauseCodeByIMSI(Long imsi) ;
	public List<Object[]> getImsiByCauseClass(int failureClass) ;
	public Collection getFailuresIds();
}
