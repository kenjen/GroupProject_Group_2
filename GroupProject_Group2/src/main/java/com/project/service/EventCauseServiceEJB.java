package com.project.service;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

import com.project.dao.EventCauseDAO;
import com.project.entities.EventCause;

@Local
@Stateless
public class EventCauseServiceEJB implements EventCauseService {
	@EJB
	// or @Inject
	private EventCauseDAO dao;
	
	public void setDao(EventCauseDAO dao) {
		this.dao = dao;
	}
	public Collection getFailuresIds(){
		return	dao.getFailures();
	}
	
	public List<Object[]> getFailuresIdsByIMSI(Long imsi) {
		return dao.getFailuresIdsByIMSI(imsi);
	}
	
	public List<Object[]> countUniqueEventCauseByModel(String phoneModel) {
		return dao.countUniqueEventCauseByModel(phoneModel);
	}
	@Override
	public List<Object[]> getCauseCodeByIMSI(Long imsi) {
		return dao.getCauseCodeByIMSI(imsi);
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
