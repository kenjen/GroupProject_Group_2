package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.UE;

@Stateless
@Local
public class JPAUeDAO implements UeDAO {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Collection<UE> getAllUEs() {
		Query query = em.createQuery("from UE");
		List<UE> result = query.getResultList();
		return result;
	}

	@Override
	public List<Object[]> getCallFailuresDateRange(Date start, Date end, Integer tac) {
		Query query = em.createNamedQuery("Ue.getCallFailuresDateRange");
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);
		query.setParameter("tac", tac);
		List<Object[]> data = query.getResultList();
		return data;
	}

	@Override
	public List<UE> getAllModels() {
		Query query = em.createNamedQuery("Ue.getAllModels");
		List<UE> data = query.getResultList();
		return data;
	}

}
