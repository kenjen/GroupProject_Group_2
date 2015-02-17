package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.EventCause;


@Local
@Stateless
public class EventCauseEJB implements EventCauseDAO{
	@PersistenceContext
	private EntityManager em;

	public Collection getFailuresIdsByIMSI(String imsi) {

		Query query = em.createNamedQuery("EventCause.findEventCauseByIMSI");
		query.setParameter("IMSI", imsi);
		List<Object[]> results = query.getResultList(); 
		List<Object[]> allCombiIds = null;

		Object[] eventCauseCombi = new Object[2];
		for(Object[] id :results){
			eventCauseCombi[0] = (Integer)id[0];
			eventCauseCombi[1] = (Integer)id[1];
			allCombiIds.add(eventCauseCombi);
		}
		return allCombiIds;


	}

}
