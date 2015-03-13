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
		for (Object o : eventCauseList) {
			entityManager.persist(o);
		}
	}

	@Override
	public void addAllFailureClass(Collection failureCauseList) {
		for (Object o : failureCauseList) {
			entityManager.persist(o);
		}
	}

	@Override
	public void addAllUe(Collection ueList) {
		for (Object o : ueList) {
			entityManager.persist(o);
		}
	}

	@Override
	public void addAllMccMnc(Collection mccMncList) {
		for (Object o : mccMncList) {
			entityManager.persist(o);
		}
	}

}
