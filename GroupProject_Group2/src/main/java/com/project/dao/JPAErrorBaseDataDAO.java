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

	@SuppressWarnings("unchecked")
	public Collection<ErrorBaseData> getAllErrorBaseData() {
		Query query = entityManager
				.createNamedQuery("ErrorBaseData.getAllErrorBaseData");
		List<ErrorBaseData> data = query.getResultList();
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void addAllErrorBaseData(Collection errorBaseDataList) {
		entityManager.createNativeQuery("truncate table error_base_data")
				.executeUpdate();
		entityManager.createNativeQuery(
				"alter table error_base_data AUTO_INCREMENT = 1")
				.executeUpdate();
		for (Object o : errorBaseDataList) {
			int i = 0;
			entityManager.persist(o);
			if (++i % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}
}
