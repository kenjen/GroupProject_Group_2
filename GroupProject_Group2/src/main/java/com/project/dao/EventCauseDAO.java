package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.project.entities.EventCause;


public interface EventCauseDAO {
	List<Object[]> getFailuresIdsByIMSI(Long imsi);
	List<Object[]> countUniqueEventCauseByModel(String phoneModel);
	List<Object[]> getCauseCodeByIMSI(Long imsi);
	Collection<EventCause> getFailures();
	List<Object[]> getImsiByCauseClass(int failureClass);
	Collection<EventCause> getAllEventCause();
	List<Object[]> countUniqueEventCauseByImsiDate(Long imsi, Date start, Date end);
	List<Object[]> getUniqueEventCauseByImsiByCauseCode(Long imsi, Integer causeCode);
}
