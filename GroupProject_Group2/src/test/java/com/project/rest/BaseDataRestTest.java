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
	BaseData baseDataRecord2 = new BaseData();
	BaseData baseDataRecord3 = new BaseData();

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

		tx.begin();
		em.joinTransaction();
		sdf.setLenient(false);
		Date date2 = sdf.parse("10-10-2013 09:01:00");
		baseDataRecord2.setDate(date2);
		baseDataRecord2.setDuration(1000);
		baseDataRecord2.setImsi(1001L);
		baseDataRecord2.setMcc(1);
		baseDataRecord2.setMnc(10);
		baseDataRecord2.setCellId(5);
		baseDataRecord2.setEventCauseFK(eventCauseRecord);
		baseDataRecord2.setMccMncFK(mccMncRecord);
		baseDataRecord2.setFaliureClassFK(failureClassRecord);
		baseDataRecord2.setUeFK(ueRecord);
		em.persist(baseDataRecord2);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();
		sdf.setLenient(false);
		Date date3 = sdf.parse("10-10-2013 09:02:00");
		baseDataRecord3.setDate(date3);
		baseDataRecord3.setDuration(1000);
		baseDataRecord3.setImsi(1002L);
		baseDataRecord3.setMcc(1);
		baseDataRecord3.setMnc(10);
		baseDataRecord3.setCellId(5);
		baseDataRecord3.setEventCauseFK(eventCauseRecord);
		baseDataRecord3.setMccMncFK(mccMncRecord);
		baseDataRecord3.setFaliureClassFK(failureClassRecord);
		baseDataRecord3.setUeFK(ueRecord);
		em.persist(baseDataRecord3);
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
		Assert.assertEquals(3, data.size());
		Assert.assertEquals(1001L, data.get(0).getImsi().longValue());
		Assert.assertEquals(eventCauseRecord, data.get(0).getEventCauseFK());
		Assert.assertEquals(1, data.get(0).getEventCauseFK().getEventId()
				.intValue());
	}

	@Test
	public void testGetAllEventCause(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final List<EventCause> data = (List<EventCause>) baseDataRest
				.getAllEventCause();
		Assert.assertEquals(3, data.size());
		Assert.assertEquals(eventCauseRecord, data.get(0));
		Assert.assertEquals(1, data.get(0).getEventId().intValue());
	}

	@Test
	public void testGetAllEventCauseByImsi(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final Long imsi = baseDataRecord.getImsi();
		final List<EventCause> data = (List<EventCause>) baseDataRest
				.getAllEventCause(imsi);

		Assert.assertEquals(2, data.size());
		Assert.assertEquals(10, data.get(0).getCauseCode().intValue());
		Assert.assertEquals(1, data.get(0).getEventId().intValue());
	}

	@Test
	public void testGetImsiBetweenDateRange(
			@ArquillianResteasyResource BaseDataRest baseDataRest)
			throws ParseException {
		final String queryCode = "c00";
		final String dateRange = "2012-02-20T09:00:002014-02-20T09:00:00";
		final String jsonRequest = queryCode + dateRange;

		final List<String[]> data = baseDataRest
				.getImsiByDateRange(jsonRequest);

		Assert.assertEquals(3, data.size());
		Assert.assertEquals(baseDataRecord.getDate().toString(), data.get(0)[3]);
		Assert.assertEquals(baseDataRecord.getImsi().toString(), data.get(0)[7]);
	}

	@Test
	public void testGetCountImsiByDateRange(
			@ArquillianResteasyResource BaseDataRest baseDataRest)
			throws ParseException {
		final String queryCode = "c00";
		final String dateRange = "2012-02-20T09:00:002014-02-20T09:00:00";
		final String jsonRequest = queryCode + dateRange;

		final List<String[]> data = baseDataRest
				.getCountImsiByDateRange(jsonRequest);

		Assert.assertEquals(2, data.size());
		Assert.assertEquals("2000", data.get(0)[4]); // duration
		Assert.assertEquals(baseDataRecord.getImsi().toString(), data.get(0)[7]); // imsi
		Assert.assertEquals("2", data.get(0)[15]); // count
	}

	@Test
	public void testGetCountSingleImsiByDateRange(
			@ArquillianResteasyResource BaseDataRest baseDataRest)
			throws ParseException {
		final String queryCode = "c00";
		final String dateRange = "2012-02-20T09:00:002014-02-20T09:00:00";
		final String imsi = "1001";
		final String jsonRequest = queryCode + dateRange + imsi;

		final List<String[]> data = baseDataRest
				.getCountSingleImsiByDateRange(jsonRequest);

		Assert.assertEquals(1, data.size());
		Assert.assertEquals(baseDataRecord.getImsi().toString(), data.get(0)[7]);
		Assert.assertEquals("2", data.get(0)[15]);
	}

	@Test
	public void testGetCountTop10ImsiByDateRange(
			@ArquillianResteasyResource BaseDataRest baseDataRest)
			throws ParseException {
		final String queryCode = "c00";
		final String dateRange = "2012-02-20T09:00:002014-02-20T09:00:00";
		final String jsonRequest = queryCode + dateRange;

		final List<String[]> data = baseDataRest
				.getCountTop10ImsiByDateRange(jsonRequest);

		Assert.assertEquals(2, data.size());
		Assert.assertEquals(baseDataRecord.getImsi().toString(), data.get(0)[7]);
		Assert.assertEquals("2", data.get(0)[15]);
	}

	@Test
	public void testGetCountTop10ComboBetweenDates(
			@ArquillianResteasyResource BaseDataRest baseDataRest)
			throws ParseException {
		final String queryCode = "c00";
		final String dateRange = "2012-02-20T09:00:002014-02-20T09:00:00";
		final String jsonRequest = queryCode + dateRange;

		final List<String[]> data = baseDataRest
				.getCountTop10ComboBetweenDates(jsonRequest);

		Assert.assertEquals(1, data.size());
		Assert.assertEquals(baseDataRecord.getCellId().toString(),
				data.get(0)[2]); // cell
		Assert.assertEquals("3", data.get(0)[15]); // count
		Assert.assertEquals(baseDataRecord.getMccMncFK().getCountry()
				.toString(), data.get(0)[16]); // country
		Assert.assertEquals(baseDataRecord.getMccMncFK().getOperator()
				.toString(), data.get(0)[17]); // operator
	}

	@Test
	public void testGetCauseCodeByIMSI(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final String queryCode = "c00";
		final String imsi = "1002";
		final String jsonRequest = queryCode + imsi;
		final List<String[]> data = baseDataRest
				.getCauseCodeByIMSI(jsonRequest);
		Assert.assertEquals(1, data.size());
		Assert.assertEquals(baseDataRecord3.getEventCauseFK().getCauseCode()
				.toString(), data.get(0)[1]);
		Assert.assertEquals("1", data.get(0)[15]); // count

	}

	@Test
	public void testGetUniqueImsi(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final List<Object> data = (List<Object>) baseDataRest.getUniqueImsi();
		Long imsi1 = (Long) data.get(0);
		Long imsi2 = (Long) data.get(1);
		Assert.assertEquals(2, data.size());
		Assert.assertEquals(baseDataRecord.getImsi().longValue(),
				imsi1.longValue());
		Assert.assertEquals(baseDataRecord3.getImsi().longValue(),
				imsi2.longValue());
	}

	@Test
	public void testCountCellFailuresByModelEventCause(
			@ArquillianResteasyResource BaseDataRest baseDataRest) {
		final String queryCode = "c00";
		final String description = "TestEvent";
		final String marketingName = "TEST MARKETING NAME";
		final String fullQueryCode = queryCode + marketingName;

		final String jsonRequest = description + "::" + fullQueryCode;

		final List<String[]> data = (List<String[]>) baseDataRest
				.countCellFailuresByModelEventCause(jsonRequest);

		Assert.assertEquals(1, data.size());
		Assert.assertEquals("5", data.get(0)[0]);
		Assert.assertEquals("3", data.get(0)[1]);
		Assert.assertEquals("3000", data.get(0)[2]);
	}

	@Test
	public void testGetAllFailuresByDate(
			@ArquillianResteasyResource BaseDataRest baseDataRest)
			throws ParseException {
		final String queryCode = "c00";
		final String dateRange = "2012-02-20T09:00:002014-02-20T09:00:00";
		final String jsonRequest = queryCode + dateRange;

		final List<String[]> data = (List<String[]>) baseDataRest
				.getAllFailuresByDate(jsonRequest);
		
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("3", data.get(0)[15]);

	}
}
