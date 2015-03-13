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

	@EJB
	private UserEquipmentDAO ueDAO;
	@EJB
	private EventCauseDAO eventCauseDAO;
	@EJB
	private FailureClassDAO failureClassDAO;

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

	public Collection<UE> addUEForeignKey() {

		List<BaseData> allBaseData = (List<BaseData>) this.getAllBaseData();
		Long l = System.nanoTime();
		Date date = new Date();
		BaseData sampleBD = new BaseData(date, 4, 4, 4, 4, 4, 4, 4, 4, "TEST",
				l, "TEST", "TEST", "TEST");
		allBaseData.add(sampleBD);
		List<UE> allUE = (List<UE>) this.getUEs();
		UE sampleUE = new UE(4, "test", "test", "test");
		allUE.add(sampleUE);
		for (BaseData o : allBaseData) {
			for (UE ue : allUE) {
				if (o.getTac().intValue() == ue.getTac()) {
					o.setUeFK(ue);
				} else {
					// NB - FOR TEST ONLY
					o.setUeFK(allUE.get(allUE.size() - 1));
				}
			}
		}
		return allUE;
	}

	public Collection<FailureClass> addFailureClassForeignKey() {
		List<BaseData> allBaseData = (List<BaseData>) this.getAllBaseData();
		Long l = System.nanoTime();
		Date date = new Date();
		BaseData bd = new BaseData(date, 3, 3, 3, 3, 3, 3, 3, 3, "TEST", l,
				"TEST", "TEST", "TEST");
		allBaseData.add(bd);
		List<FailureClass> failureClasses = (List<FailureClass>) this
				.getFailureClasses();
		FailureClass sampleFC = new FailureClass(3, "test");
		failureClasses.add(sampleFC);
		for (BaseData o : allBaseData) {
			for (FailureClass fc : failureClasses) {
				if (o.getFailureClass().intValue() == fc.getFailureClass()) {
					o.setFaliureClassFK(fc);
				} else {
					// NB - FOR TEST ONLY
					o.setFaliureClassFK(failureClasses.get(failureClasses
							.size() - 1));
				}
			}
		}
		return failureClasses;
	}

	@Override
	public Collection<FailureClass> getFailureClasses() {
		return failureClassDAO.getAllFailureClasses();
	}

	@Override
	public Collection<UE> getUEs() {
		return ueDAO.getAllUEs();
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
