package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.MccMnc;

@Stateless
@Local
public class JPAMccMncDAO implements MccMncDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addAllMccMnc(Collection mccMncList) {
		for (Object o : mccMncList) {
			entityManager.persist(o);
		}
	}

	@Override
	public Collection<MccMnc> getAllMccMnc() {
		Query query = entityManager.createNamedQuery("MccMnc.getAllMccMnc");
		List<MccMnc> result = query.getResultList();
		return result;
	}

}
