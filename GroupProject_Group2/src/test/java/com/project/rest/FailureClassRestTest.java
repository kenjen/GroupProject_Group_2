package com.project.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;

@RunWith(Arquillian.class)
public class FailureClassRestTest {
	
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
		em.createQuery("delete from BaseData").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from FailureClass").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from MccMnc").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		FailureClass failureClass = new FailureClass(1, "test");
		MccMnc mccMnc = new MccMnc(1, 1, "testCountry", "testOperator");
		BaseData b = new BaseData();
		b.setMccMncFK(mccMnc);
		b.setFaliureClassFK(failureClass);
		b.setCellId(1);
		b.setDate(new Date());
		tx.begin();
		em.joinTransaction();
		em.persist(failureClass);
		em.persist(mccMnc);
		em.persist(b);
		tx.commit();
		em.clear();
		
		failureClass = null;
		mccMnc = null;
		b = null;
	}

	private void beginTransaction() throws Exception {
		tx.begin();
		em.joinTransaction();
	}

	@After
	public void endTransaction() throws Exception {
		//clearDataFromPersistenceModule();
		tx.commit();
	}
	
	@Test
	public void getAllFailureClassesTest(@ArquillianResteasyResource FailureClassRest failureClassRest){
		List<FailureClass> failureClasses = (List<FailureClass>) failureClassRest.getAllFailures();
		assertEquals(1, failureClasses.size());
		assertTrue(failureClasses.get(0).getFailureClass() == 1);
		assertEquals(failureClasses.get(0).getDescription(), "test");
		
		failureClasses = null;
	}
	
	@Test
	public void specificFailuresForTopTenCombiTest(@ArquillianResteasyResource FailureClassRest failureClassRest) throws ParseException{
		String code = "c00";
		String country = "testCountry,";
		String operator = "testOperator,";
		String cellId = "1,";
		String startDate = "2015-04-01T09:00:00";
		String endDate = "2025-01-01T09:00:00";
		String data = country+ operator + cellId + code+startDate + endDate;
		
		List<String[]> info = failureClassRest.specificFailuresForTopTenCombi(data);

		assertEquals(1, info.size());
		assertEquals(info.get(0)[0], "1");
		assertEquals(info.get(0)[1], "test");
		assertEquals(info.get(0)[2], "1");
		
		info = null;
	}
	
	@Test
	public void specificFailuresForTopTenCombiErrorTest(@ArquillianResteasyResource FailureClassRest failureClassRest) throws ParseException{
		List<String[]> info = failureClassRest.specificFailuresForTopTenCombi("***,****,1,**************************************");
		assertEquals(null, info);
		info = null;
	}
}
