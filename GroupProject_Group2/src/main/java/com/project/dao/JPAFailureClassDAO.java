package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.FailureClass;

@Stateless
@Local
public class JPAFailureClassDAO implements FailureClassDAO {

	@PersistenceContext
	private EntityManager em;

	public Collection<FailureClass> getAllFailureClasses() {
		Query query = em.createQuery("from FailureClass");
		List<FailureClass> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> specificFailuresForTopTenCombi(String country,
			String operator, Integer cellId, Date startDate, Date endDate) {
		Query query = em
				.createNamedQuery("FailureClass.specificFailuresForTopTenCombi");
		query.setParameter("country", country);
		query.setParameter("operator", operator);
		query.setParameter("cellId", cellId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

}
