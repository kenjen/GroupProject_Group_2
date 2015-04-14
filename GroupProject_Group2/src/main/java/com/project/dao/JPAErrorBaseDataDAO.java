package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.ErrorBaseData;

@Stateless
@Local
public class JPAErrorBaseDataDAO implements ErrorBaseDataDAO {

	@PersistenceContext(unitName = "GroupProject_Group2")
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public Collection<ErrorBaseData> getAllErrorBaseData() {
		Query query = entityManager
				.createNamedQuery("ErrorBaseData.getAllErrorBaseData");
		List<ErrorBaseData> data = query.getResultList();
		return data;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void addAllErrorBaseData(Collection errorBaseDataList) {
		for (Object o : errorBaseDataList) {
			entityManager.persist(o);
		}
	}
}
