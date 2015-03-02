package com.project.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local
public class JPAUeDAO implements UeDAO {
	
	@PersistenceContext(unitName="GroupProject_Group2") EntityManager entityManager;

	@Override
	public List<Object[]> getCallFailuresDateRange(Date start, Date end, Integer tac) {
		Query query = entityManager.createNamedQuery("Ue.getCallFailuresDateRange");
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);
		query.setParameter("tac", tac);
		List<Object[]> data = query.getResultList();
		return data;
	}

}
