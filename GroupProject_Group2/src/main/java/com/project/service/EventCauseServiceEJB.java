package com.project.service;
import java.util.Collection;

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
	
	public Collection getFailuresIdsByIMSI(Long imsi) {
		return dao.getFailuresIdsByIMSI(imsi);
	}

}
