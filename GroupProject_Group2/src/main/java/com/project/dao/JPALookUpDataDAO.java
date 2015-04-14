package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;

@Stateless
@Local
public class JPALookUpDataDAO implements LookUpDataDAO {

	@PersistenceContext(unitName = "GroupProject_Group2")
	private EntityManager entityManager;
	@EJB
	private EventCauseDAO evDao;
	@EJB
	private FailureClassDAO fcDao;
	@EJB
	private UeDAO ueDao;
	@EJB
	private MccMncDAO mccDao;

	@Override
	public void addAllEventCause(Collection<EventCause> eventCauseList) {
		List<EventCause> evDbList = (List<EventCause>) evDao.getAllEventCause();
		for (EventCause o : eventCauseList) {
			if (!evDbList.contains(o))
				entityManager.persist(o);
			else
				;
		}
	}

	@Override
	public void addAllFailureClass(Collection<FailureClass> failureCauseList) {
		List<FailureClass> fcDbList = (List<FailureClass>) fcDao
				.getAllFailureClasses();
		for (FailureClass o : failureCauseList) {
			if (!fcDbList.contains(o))
				entityManager.persist(o);
			else
				;
		}
	}

	@Override
	public void addAllUe(Collection<UE> ueList) {
		List<UE> ueDbList = ueDao.getAllModels();
		for (UE o : ueList) {
			if (!ueDbList.contains(o))
				entityManager.persist(o);
			else
				;
		}
	}

	@Override
	public void addAllMccMnc(Collection<MccMnc> mccMncList) {
		List<MccMnc> mccDbList = (List<MccMnc>) mccDao.getAllMccMnc();
		for (MccMnc o : mccMncList) {
			if (!mccDbList.contains(o))
				entityManager.persist(o);
			else
				;
		}
	}

}
