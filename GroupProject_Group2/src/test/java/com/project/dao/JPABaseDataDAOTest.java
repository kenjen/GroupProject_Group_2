package com.project.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.MccMnc;

@RunWith(Arquillian.class)
public class JPABaseDataDAOTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}

	@EJB
	private BaseDataDAO baseDataDao;

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(JPABaseDataDAOTest.class);

	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		clearDataFromPersistenceModule();
		insertTestData();
		bulkTestDataLoader();
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
		EventCause eventCauseRecord = new EventCause();
		eventCauseRecord.setCauseCode(10);
		eventCauseRecord.setEventId(1);
		eventCauseRecord.setDescription("TestEvent");

		em.persist(eventCauseRecord);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();
		MccMnc mccMncRecord = new MccMnc();
		mccMncRecord.setMcc(1);
		mccMncRecord.setMnc(10);
		mccMncRecord.setCountry("TestCountry");
		mccMncRecord.setOperator("TestOperator");

		em.persist(mccMncRecord);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();

		sdf.setLenient(false);
		Date date = sdf.parse("10-10-2013 09:00:00");
		BaseData baseDataRecord = new BaseData();
		baseDataRecord.setDate(date);
		baseDataRecord.setDuration(1000);
		baseDataRecord.setImsi(1001L);
		baseDataRecord.setMcc(1);
		baseDataRecord.setMnc(10);
		baseDataRecord.setCellId(5);
		baseDataRecord.setEventCauseFK(eventCauseRecord);
		baseDataRecord.setMccMncFK(mccMncRecord);

		em.persist(baseDataRecord);
		tx.commit();
		em.clear();
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	private Collection bulkTestDataLoader() throws Exception {
		List<BaseData> list = new ArrayList<BaseData>();
		for (int i = 0; i < 10; ++i) {
			BaseData b = new BaseData();
			Date date = new Date();
			date.setYear(2014);
			if (i % 2 == 0) {
				date.setMonth(i);
			}
			date.setMinutes(i);
			b.setDate(new Date());
			b.setImsi(100L + i);
			list.add(b);
		}
		return list;
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
	public void testThatWeCanAddData() {
		List<BaseData> dbList = (List<BaseData>) baseDataDao.getAllBaseData();
		assertEquals("The number of objects in the database does not match", 1,
				dbList.size());
	}

	@Test
	public void testToAddData() throws Exception {
		baseDataDao.addAllBaseData(bulkTestDataLoader());
		assertEquals("The number of objects in the database does not match",
				11, baseDataDao.getAllBaseData().size());
	}

	@Test
	public void testGetImsiByDateRange() throws ParseException {
		String theImsi = "";
		Date firstDateInRange = null;
		Date secondDateInRange = null;
		sdf.setLenient(false);
		firstDateInRange = sdf.parse("01-10-2013 09:00:00");
		secondDateInRange = sdf.parse("01-11-2013 09:00:00");

		List<Object[]> imsiList = baseDataDao.getImsiByDateRange(
				firstDateInRange, secondDateInRange);
		for (Object[] o : imsiList) {
			theImsi = Objects.toString(o[1]);
		}
		assertEquals(
				"The number of objects in the list returned does not match the expected number",
				1, imsiList.size());
		assertEquals("The IMSI does not match expected number", 1001L,
				Long.parseLong(theImsi));

	}

	@Test
	public void testCountImsiBetweenDateRange() throws ParseException {
		String theCount = "";
		String theImsi = "";
		Date firstDateInRange = null;
		Date secondDateInRange = null;
		sdf.setLenient(false);
		firstDateInRange = sdf.parse("01-10-2013 09:00:00");
		secondDateInRange = sdf.parse("01-11-2013 09:00:00");

		List<Object[]> imsiList = baseDataDao.getCountImsiBetweenDates(
				firstDateInRange, secondDateInRange);
		for (Object[] o : imsiList) {
			theCount = Objects.toString(o[0]);
			theImsi = Objects.toString(o[2]);
		}
		assertEquals(
				"The number of objects in the list returned does not match the expected number",
				1, imsiList.size());
		assertEquals("The count does not match expected number", 1,
				Integer.parseInt(theCount));
		assertEquals("The imsi returned does not match the expected number",
				1001L, Long.parseLong(theImsi));
	}

	@Test
	public void testCountSingleImsiBetweenDates() throws ParseException {
		String theCount = "";
		String theImsi = "";
		Long enterTheImsi = 1001L;
		Date firstDateInRange = null;
		Date secondDateInRange = null;
		sdf.setLenient(false);
		firstDateInRange = sdf.parse("01-10-2013 09:00:00");
		secondDateInRange = sdf.parse("01-11-2013 09:00:00");

		List<Object[]> imsiList = baseDataDao.getCountSingleImsiBetweenDates(
				firstDateInRange, secondDateInRange, enterTheImsi);
		for (Object[] o : imsiList) {
			theCount = Objects.toString(o[0]);
			theImsi = Objects.toString(o[1]);
		}
		assertEquals(
				"The number of objects in the list returned does not match the expected number",
				1, imsiList.size());
		assertEquals("The count does not match expected number", 1,
				Integer.parseInt(theCount));
		assertEquals("The imsi returned does not match the expected number",
				1001L, Long.parseLong(theImsi));
	}

	@Test
	public void testCountTop10ImsiBetweenDates() throws ParseException {
		String theCount = "";
		String theImsi = "";
		Date firstDateInRange = null;
		Date secondDateInRange = null;
		sdf.setLenient(false);
		firstDateInRange = sdf.parse("01-10-2013 09:00:00");
		secondDateInRange = sdf.parse("01-11-2013 09:00:00");

		List<Object[]> imsiList = baseDataDao.getCountTop10ImsiBetweenDates(
				firstDateInRange, secondDateInRange);
		for (Object[] o : imsiList) {
			theCount = Objects.toString(o[0]);
			theImsi = Objects.toString(o[1]);
		}
		assertEquals(
				"The number of objects in the list returned does not match the expected number",
				1, imsiList.size());
		assertEquals("The count does not match expected number", 1,
				Integer.parseInt(theCount));
		assertEquals("The imsi returned does not match the expected number",
				1001L, Long.parseLong(theImsi));
	}

	@Test
	public void testCountTop10ComboBetweenDates() throws ParseException {
		// count(*), m.country, m.operator, b.cellId
		String theCount = "";
		String theCountry = "";
		String theOperator = "";
		String theCell = "";
		Date firstDateInRange = null;
		Date secondDateInRange = null;
		sdf.setLenient(false);
		firstDateInRange = sdf.parse("01-10-2013 09:00:00");
		secondDateInRange = sdf.parse("01-11-2013 09:00:00");

		List<Object[]> imsiList = baseDataDao.getCountTop10ComboBetweenDates(
				firstDateInRange, secondDateInRange);
		for (Object[] o : imsiList) {
			theCount = Objects.toString(o[0]);
			theCountry = Objects.toString(o[1]);
			theOperator = Objects.toString(o[2]);
			theCell = Objects.toString(o[3]);
		}
		assertEquals(
				"The number of objects in the list returned does not match the expected number",
				1, imsiList.size());
		assertEquals("The count does not match expected number", 1,
				Integer.parseInt(theCount));
		assertEquals(
				"The country returned does not match the expected country",
				"TestCountry", theCountry);
		assertEquals(
				"The operator returned does not match the expected operator",
				"TestOperator", theOperator);
		assertEquals("The imsi returned does not match the expected number", 5,
				Integer.parseInt(theCell));
	}

	@Test
	public void testFindUniqueCauseByIMSI() {
		String theCauseCode = "";
		long enterTheImsi = 1001L;

		List<Object[]> imsiList = baseDataDao
				.getfindUniqueCauseByIMSI(enterTheImsi);
		for (Object[] o : imsiList) {
			theCauseCode = Objects.toString(o[1]);
		}
		assertEquals(
				"The number of objects in the list returned does not match the expected number",
				1, imsiList.size());
		assertEquals("The cause code does not match expected number", 10,
				Integer.parseInt(theCauseCode));
	}
}
