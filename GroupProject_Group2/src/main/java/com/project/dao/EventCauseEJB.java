package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.EventCause;

@Local
@Stateless
public class EventCauseEJB implements EventCauseDAO {
	@PersistenceContext
	private EntityManager em;

	
	@Override
	public List<Object[]> getFailuresIdsByIMSI(Long imsi) {
		Query query = em.createNamedQuery("findEventCauseByIMSI");
		query.setParameter("IMSI", imsi);
		return query.getResultList();
	}


	@Override
	public Collection<EventCause> getAllEventCause() {
		Query query = em.createNamedQuery("EventCause.getAllEventCause");
		List<EventCause> data = query.getResultList();
		return data;
	}
	@Override
	public List<Object[]> getImsiByCauseClass(int failureClass) {
		Query query = em.createNamedQuery("FailureClass.getImsiByCauseClass");
		query.setParameter("failureClass", failureClass);
		return query.getResultList();
	}


	
	@Override
	public List<Object[]> countUniqueEventCauseByModel(String phoneModel) {
		Query query = em.createNamedQuery("countUniqueEventCauseByModel");
		query.setParameter("phoneModel", phoneModel);
		return query.getResultList();
		
	}

	@Override
	public List<Object[]> countUniqueEventCauseByImsiDate(Long imsi, Date start, Date end) {
		Query query = em.createNamedQuery("EventCause.countUniqueEventCauseByImsiDate");
		query.setParameter("imsi", imsi);
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getUniqueEventCauseByImsiByCauseCode(Long imsi, Integer causeCode) {
		Query query = em.createNamedQuery("EventCause.getUniqueEventCauseByImsiByCauseCode");
		query.setParameter("imsi", imsi);
		query.setParameter("causeCode", causeCode);
		return query.getResultList();
	}
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
