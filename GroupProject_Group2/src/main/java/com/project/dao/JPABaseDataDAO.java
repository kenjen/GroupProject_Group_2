package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;

@Stateless
@Local
public class JPABaseDataDAO implements BaseDataDAO {

	@PersistenceContext(unitName = "GroupProject_Group2")
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<BaseData> getAllBaseData() {
		Query query = entityManager.createNamedQuery("BaseData.getAllBaseData");
		List<BaseData> data = query.getResultList();
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void addAllBaseData(Collection baseDataList) {
		int i = 0;
		for (Object o : baseDataList) {
			entityManager.persist(o);
			if (++i % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getImsiByDateRange(Date startDate, Date endDate) {
		Query query = entityManager.createNamedQuery("BaseData.getImsiBetweenDates");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		List<Object[]> data = query.getResultList();
		return data;
	}

	@Override
	public List<Object[]> getCountImsiBetweenDates(Date startDate, Date endDate) {
		Query query = entityManager.createNamedQuery("BaseData.getCountImsiBetweenDates");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		List<Object[]> data = query.getResultList();
		return data;
	}

	@Override
	public List<Object[]> getCountSingleImsiBetweenDates(Date startDate, Date endDate, long imsi) {
		Query query = entityManager.createNamedQuery("BaseData.getCountSingleImsiBetweenDates");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("imsi", imsi);
		List<Object[]> data = query.getResultList();
		return data;
	}

	@Override
	public List<Object[]> getCountTop10ImsiBetweenDates(Date startDate,
			Date endDate) {
		Query query = entityManager.createNamedQuery("BaseData.getCountTop10ImsiBetweenDates");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		List<Object[]> data = query.setMaxResults(10).getResultList();
		return data;
	}

	@Override
	public List<Object[]> getCountTop10ComboBetweenDates(Date startDate,
			Date endDate) {
		Query query = entityManager.createNamedQuery("BaseData.getCountTop10ComboBetweenDates");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		List<Object[]> data = query.setMaxResults(10).getResultList();
		return data;
	}
	@Override
	public List<Object[]> getfindUniqueCauseByIMSI(long imsi) {
		Query query = entityManager.createNamedQuery("BaseData.getfindUniqueCauseByIMSI");
		query.setParameter("imsi", imsi);
		List<Object[]> data = query.getResultList();
		return data;
	}

}
