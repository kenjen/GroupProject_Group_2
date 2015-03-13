package com.project.dao;

import java.util.Collection;
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

	
	public List<Object[]> getFailuresIdsByIMSI(Long imsi) {
		Query query = em.createNamedQuery("findEventCauseByIMSI");
		query.setParameter("IMSI", imsi);
		return query.getResultList();
	}

	public Collection getFailures() {
		Query query = em.createNamedQuery("findEventCause");
		return query.getResultList();
	}

	@Override
	public Collection getAllEventCause() {
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


	
	public List<Object[]> countUniqueEventCauseByModel(String phoneModel) {
		Query query = em.createNamedQuery("countUniqueEventCauseByModel");
		query.setParameter("phoneModel", phoneModel);
		return query.getResultList();
		
	}

	@Override
	public List<Object[]> getCauseCodeByIMSI(Long imsi) {
		Query query = em.createNamedQuery("findUniqueCauseByIMSI");
		query.setParameter("IMSI", imsi);
		return query.getResultList();
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
