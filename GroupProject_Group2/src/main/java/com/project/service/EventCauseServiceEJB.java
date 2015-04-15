package com.project.service;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import com.project.dao.EventCauseDAO;

@Local
@Stateless
public class EventCauseServiceEJB implements EventCauseService {
	@EJB
	private EventCauseDAO dao;
	
	@Override
	public List<Object[]> getFailuresIdsByIMSI(Long imsi) {
		return dao.getFailuresIdsByIMSI(imsi);
	}
	
	@Override
	public List<Object[]> countUniqueEventCauseByModel(String phoneModel) {
		return dao.countUniqueEventCauseByModel(phoneModel);
	}
	@Override
	public List<Object[]> getImsiByCauseClass(int failureClass) {
		return dao.getImsiByCauseClass(failureClass);
	}
	@Override
	public List<Object[]> countUniqueEventCauseByImsiDate(Long imsi, Date start, Date end) {
		return dao.countUniqueEventCauseByImsiDate(imsi, start, end);
	}
	@Override
	public List<Object[]> getUniqueEventCauseByImsiByCauseCode(Long imsi, Integer causeCode) {
		return dao.getUniqueEventCauseByImsiByCauseCode(imsi, causeCode);
	}

}
