package com.project.rest;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import junit.framework.Assert;

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
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;

@RunWith(Arquillian.class)
public class BaseDataRestTest {

	@Deployment
	public static WebArchive createDeployment() {

		PomEquippedResolveStage pom = Maven.resolver()
				.loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();

		File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity()
				.asFile();

		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackages(true, "com.project")
				.addAsLibraries(libraries)
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

	EventCause eventCauseRecord = new EventCause();
	MccMnc mccMncRecord = new MccMnc();
	FailureClass failureClassRecord = new FailureClass();
	UE ueRecord = new UE();
	BaseData baseDataRecord = new BaseData();

	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

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
		em.createQuery("delete from EventCause").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from FailureClass").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from UE").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from MccMnc").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		tx.begin();
		em.joinTransaction();
		eventCauseRecord.setCauseCode(10);
		eventCauseRecord.setEventId(1);
		eventCauseRecord.setDescription("TestEvent");
		em.persist(eventCauseRecord);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();
		mccMncRecord.setMcc(1);
		mccMncRecord.setMnc(10);
		mccMncRecord.setCountry("TestCountry");
		mccMncRecord.setOperator("TestOperator");
		em.persist(mccMncRecord);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();
		failureClassRecord.setFailureClass(1);
		failureClassRecord.setDescription("TEST FAILURE");
		em.persist(failureClassRecord);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();
		ueRecord.setAccessCapability("TEST ACCESS CAPABILITY");
		ueRecord.setManufacturer("TEST MANUFACTURER");
		ueRecord.setMarketingName("TEST MARKETING NAME");
		ueRecord.setTac(1001);
		em.persist(ueRecord);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();
		sdf.setLenient(false);
		Date date = sdf.parse("10-10-2013 09:00:00");
		baseDataRecord.setDate(date);
		baseDataRecord.setDuration(1000);
		baseDataRecord.setImsi(1001L);
		baseDataRecord.setMcc(1);
		baseDataRecord.setMnc(10);
		baseDataRecord.setCellId(5);
		baseDataRecord.setEventCauseFK(eventCauseRecord);
		baseDataRecord.setMccMncFK(mccMncRecord);
		baseDataRecord.setFaliureClassFK(failureClassRecord);
		baseDataRecord.setUeFK(ueRecord);
		em.persist(baseDataRecord);
		tx.commit();
		em.clear();
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
	public void testGetAllBaseData(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final List<BaseData> data = (List<BaseData>) baseDataRest
				.getAllBaseData();
		assert (data.size() == 1);
		Assert.assertEquals(1, data.size());
		assert (data.get(0).getImsi().equals(1001L));
		assert (data.get(0).getEventCauseFK().equals(eventCauseRecord));
		assert (data.get(0).getEventCauseFK().getEventId().equals(1));
	}

	@Test
	public void testGetAllEventCause(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final List<EventCause> data = (List<EventCause>) baseDataRest
				.getAllEventCause();
		assert (data.size() == 1);
		Assert.assertEquals(1, data.size());
		assert (data.get(0).equals(eventCauseRecord));
		assert (data.get(0).getEventId().equals(1));
	}

	@Test
	public void testGetAllEventCauseByImsi(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final Long imsi = baseDataRecord.getImsi();
		final List<EventCause> data = (List<EventCause>) baseDataRest
				.getAllEventCause(imsi);
		Assert.assertEquals(1, data.size());
		Assert.assertEquals(10, data.get(0).getCauseCode().intValue());
	}
}
