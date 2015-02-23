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
			try{
				entityManager.persist(o);
			}catch(Exception e){
				//object already in database
			}
		}
	}

	@Override
	public void addAllFailureClass(Collection failureCauseList) {
		for (Object o : failureCauseList) {
			try{
				entityManager.persist(o);
			}catch(Exception e){
				//object already in database
			}
		}
	}

	@Override
	public void addAllUe(Collection ueList) {
		for (Object o : ueList) {
			try{
				entityManager.persist(o);
			}catch(Exception e){
				//object already in database
			}
		}
	}

	@Override
	public void addAllMccMnc(Collection mccMncList) {
		for (Object o : mccMncList) {
			try{
				entityManager.persist(o);
			}catch(Exception e){
				//object already in database
			}
		}
	}

}
