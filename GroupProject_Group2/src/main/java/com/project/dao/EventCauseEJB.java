package com.project.dao;

import java.util.ArrayList;
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

	public Collection getFailuresIdsByIMSI(Long imsi) {
		Query query = em.createNamedQuery("findEventCauseByIMSI");
		query.setParameter("IMSI", imsi);
		return query.getResultList();
	}
	
	public Collection getFailures(){
		Query query = em.createNamedQuery("findEventCause"); 
		return query.getResultList();
	}

}
