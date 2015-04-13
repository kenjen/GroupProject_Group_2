package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.project.entities.EventCause;


public interface EventCauseDAO {
	List<Object[]> getFailuresIdsByIMSI(Long imsi);
	List<Object[]> countUniqueEventCauseByModel(String phoneModel);
	List<Object[]> getImsiByCauseClass(int failureClass);
	List<Object[]> countUniqueEventCauseByImsiDate(Long imsi, Date start, Date end);
	List<Object[]> getUniqueEventCauseByImsiByCauseCode(Long imsi, Integer causeCode);
	Collection<EventCause> getAllEventCause();
}
