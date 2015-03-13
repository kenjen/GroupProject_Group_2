package com.project.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.EventCause;
@RunWith(Arquillian.class)
public class ArqEventCauseEJBTest {
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}
	@EJB
	private EventCauseDAO eventCauseDao;
	@EJB
	private BaseDataDAO jpaBaseDataDao;

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		clearDataFromPersistenceModule();
		insertTestData();
		beginTransaction();
	}

	private void clearDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("findEventCauseByIMSI").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception {
		tx.begin();
		em.joinTransaction();
		EventCause eventCause = new EventCause();
		eventCause.setEventId(4200);
		eventCause.setCauseCode(1);
		eventCause.setId(2);
		BaseData baseDataRecord = new BaseData();
		baseDataRecord.setImsi(20L);
		baseDataRecord.setEventCauseFK(eventCause);
		em.persist(baseDataRecord);
		em.persist(eventCause);
		tx.commit();
		em.clear();
	}

	private List bulkLoader() throws Exception {
		List eventCauseList = new ArrayList<EventCause>();
		for (int i = 0; i < 10; ++i) {
			EventCause eventCause = new EventCause();
			eventCause.setEventId(4320);
			eventCauseList.add(eventCause);
		}
		return eventCauseList;
	}

	private void beginTransaction() throws Exception {
		tx.begin();
		em.joinTransaction();
	}

	@After
	public void endTransaction() throws Exception {
		tx.commit();
	}

	

	@Test
	public void testGetFailuresIdsByIMSI(){
		List<Object[]> failuresByImsi = eventCauseDao.getFailuresIdsByIMSI(20L);
		List<Object[]> expectedFailuresByImsi = new ArrayList<Object[]>();
		Integer[] element = {4200, 2};
		expectedFailuresByImsi.add(element);
		assertSame(failuresByImsi,expectedFailuresByImsi);
	
	}
}
