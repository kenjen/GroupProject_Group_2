package com.project.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;

@RunWith(Arquillian.class)
public class FailureClassRestTest {


	@Deployment
	public static WebArchive createDeployment() {
		
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
		
		File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class,"test.war")
				.addPackages(true, "com.project")
				.addAsLibraries(libraries)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
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
	}
	
	@Test
	public void specificFailuresForTopTenCombiTest(@ArquillianResteasyResource FailureClassRest failureClassRest) throws ParseException{
		final String code = "c00";
		final String country = "testCountry,";
		final String operator = "testOperator,";
		final String cellId = "1,";
		final String startDate = "2015-04-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";
		final String data = country+ operator + cellId + code+startDate + endDate;
		
		final List<String[]> info = failureClassRest.specificFailuresForTopTenCombi(data);

		assertEquals(1, info.size());
		assertEquals(info.get(0)[0], "1");
		assertEquals(info.get(0)[1], "test");
		assertEquals(info.get(0)[2], "1");
	}
	
	@Test
	public void specificFailuresForTopTenCombiErrorTest(@ArquillianResteasyResource FailureClassRest failureClassRest) throws ParseException{
		final List<String[]> info = failureClassRest.specificFailuresForTopTenCombi("***,****,1,**************************************");
		assertEquals(null, info);

	}
}
