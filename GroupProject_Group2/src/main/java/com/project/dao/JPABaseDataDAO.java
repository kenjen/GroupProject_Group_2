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

	@PersistenceContext(unitName="GroupProject_Group2")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<BaseData> getAllBaseData() {
		Query query = entityManager.createNamedQuery("BaseData.getAllBaseData");
		List<BaseData> data = query.getResultList();
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void addAllBaseData(Collection baseDataList) {
		//if(entityManager!=null){
			//entityManager.getTransaction().begin();
			for (Object o : baseDataList) {
				entityManager.persist(o);
			}
			//entityManager.getTransaction().commit();
			//entityManager.close();
		//}
	}

}
