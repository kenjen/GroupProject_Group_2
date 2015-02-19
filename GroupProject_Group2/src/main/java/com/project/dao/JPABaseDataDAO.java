package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.BaseData;

@Stateless
@Local
public class JPABaseDataDAO implements BaseDataDAO {

	@PersistenceContext(unitName="GroupProject_Group2") EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<BaseData> getAllBaseData() {
		Query query = entityManager.createNamedQuery("BaseData.getAllBaseData");
		List<BaseData> data = query.getResultList();
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void addAllBaseData(Collection baseDataList) {
		entityManager.createNativeQuery("truncate table base_data").executeUpdate();
		entityManager.createNativeQuery("alter table base_data AUTO_INCREMENT = 1").executeUpdate();
		for (Object o : baseDataList) {
			entityManager.persist(o);
		}
	}

}
