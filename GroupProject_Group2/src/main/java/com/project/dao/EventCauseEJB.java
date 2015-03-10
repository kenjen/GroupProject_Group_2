package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.EventCause;

/**
 * @javadoc 
 * author D14125320
 *
 */

@Local
@Stateless
public class EventCauseEJB implements EventCauseDAO {
	@PersistenceContext
	EntityManager em;

	/* retrieve results for query to get the event and cause ids combinations 
	 * taking in the IMSI as parameter(non-Javadoc)
	 * @see com.project.dao.EventCauseDAO#getFailuresIdsByIMSI(java.lang.Long)
	 */
	public List<Object[]> getFailuresIdsByIMSI(Long imsi) {
		Query query = em.createNamedQuery("findEventCauseByIMSI");
		query.setParameter("IMSI", imsi);
		return query.getResultList();
	}
	/*get results of query to retrieve to get all failures
	 * (non-Javadoc)
	 * @see com.project.dao.EventCauseDAO#getFailures()
	 */
	public Collection getFailures() {
		Query query = em.createNamedQuery("findEventCause");
		return query.getResultList();
	}
	/* get results to retrieve all the columns in the event cause table
	 * (non-Javadoc)
	 * @see com.project.dao.EventCauseDAO#getAllEventCause()
	 */
	@Override
	public Collection getAllEventCause() {
		Query query = em.createNamedQuery("EventCause.getAllEventCause");
		List<EventCause> data = query.getResultList();
		return data;
	}
	/* retrieve results for query to get the IMSI's 
	 * taking in the failure class as parameter
	 * @see com.project.dao.EventCauseDAO#getImsiByCauseClass(java.lang.int)
	 */
	@Override
	public List<Object[]> getImsiByCauseClass(int failureClass) {
		Query query = em.createNamedQuery("FailureClass.getImsiByCauseClass");
		query.setParameter("failureClass", failureClass);
		return query.getResultList();
	}

	/* 
	 * retrieve results for query to count unique event and cause ids combinations 
	 * taking in the phoneModel as parameter(non-Javadoc)
	 * @see com.project.dao.EventCauseDAO#countUniqueEventCauseByModel(java.lang.String)
	 */
	
	public List<Object[]> countUniqueEventCauseByModel(String phoneModel) {
		Query query = em.createNamedQuery("countUniqueEventCauseByModel");
		query.setParameter("phoneModel", phoneModel);
		return query.getResultList();
		
	}
	/*
	 * retrieve results for query to get  unique cause id
	 * taking in the IMSI as parameter(non-Javadoc)
	 * @see com.project.dao.EventCauseDAO#getCauseCodeByIMSI(java.lang.Long)
	 */
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
