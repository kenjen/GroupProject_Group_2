package com.project.dao;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings("rawtypes")
@Stateless
@Local
public class JPALookUpDataDAO implements LookUpDataDAO {

	@PersistenceContext(unitName = "GroupProject_Group2")
	private EntityManager entityManager;

	@Override
	public void addAllEventCause(Collection eventCauseList) {
		int i = 0;
		for (Object o : eventCauseList) {
			entityManager.persist(o);
			if (++i % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Override
	public void addAllFailureClass(Collection failureCauseList) {
		int i = 0;
		for (Object o : failureCauseList) {
			entityManager.persist(o);
			if (++i % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Override
	public void addAllUe(Collection ueList) {
		int i = 0;
		for (Object o : ueList) {
			entityManager.persist(o);
			if (++i % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Override
	public void addAllMccMnc(Collection mccMncList) {
		int i = 0;
		for (Object o : mccMncList) {
			entityManager.persist(o);
			if (++i % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

}
