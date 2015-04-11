package com.project.validator;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.dao.EventCauseDAO;
import com.project.dao.FailureClassDAO;
import com.project.dao.MccMncDAO;
import com.project.dao.UeDAO;
import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;
import com.project.reader.excel.ExcellValidator;

@RunWith(Arquillian.class)
public class ExcellValidatorTest {

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

	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

	@EJB
	private EventCauseDAO eventCauseDao;
	@EJB
	private FailureClassDAO failureClassDao;
	@EJB
	private UeDAO ueDao;
	@EJB
	private MccMncDAO mccMncDao;

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
		em.persist(anEventCauseRecord());
		tx.commit();
		em.clear();
		tx.begin();
		em.joinTransaction();
		em.persist(aFailureClassRecord());
		tx.commit();
		em.clear();
		tx.begin();
		em.joinTransaction();
		em.persist(aUeRecord());
		tx.commit();
		em.clear();
		tx.begin();
		em.joinTransaction();
		em.persist(anMccMncRecord());
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

	private EventCause anEventCauseRecord() {
		EventCause eventCauseRecord = new EventCause();
		eventCauseRecord.setCauseCode(10);
		eventCauseRecord.setEventId(1);
		eventCauseRecord.setDescription("TestEvent");
		return eventCauseRecord;
	}

	private FailureClass aFailureClassRecord() {
		FailureClass failureClassRecord = new FailureClass();
		failureClassRecord.setFailureClass(10);
		failureClassRecord.setDescription("TestFailureClass");
		return failureClassRecord;
	}

	private MccMnc anMccMncRecord() {
		MccMnc mccMncRecord = new MccMnc();
		mccMncRecord.setMcc(1);
		mccMncRecord.setMnc(10);
		mccMncRecord.setCountry("TestCountry");
		mccMncRecord.setOperator("TestOperator");
		return mccMncRecord;
	}

	private UE aUeRecord() {
		UE ueRecord = new UE();
		ueRecord.setTac(100);
		ueRecord.setManufacturer("TestManufacturer");
		ueRecord.setMarketingName("TestMarketingName");
		ueRecord.setAccessCapability("TestAccessCapability");
		return ueRecord;
	}

	private BaseData validBaseData() {
		sdf.setLenient(false);
		Date date = null;
		try {
			date = sdf.parse("01-01-2015 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BaseData bd = new BaseData(date, 1, 10, 101000, 1, 10, 4, 1000, 10,
				"13B", 111111111111115L, "123", "123", "123");
		
		List<EventCause> evList = (List<EventCause>) eventCauseDao
				.getAllEventCause();
		bd.setEventCauseFK(evList.get(0));
		List<FailureClass> fcList = (List<FailureClass>) failureClassDao
				.getAllFailureClasses();
		bd.setFaliureClassFK(fcList.get(0));
		List<UE> ueList = (List<UE>) ueDao.getAllUEs();
		bd.setUeFK(ueList.get(0));
		List<MccMnc> mcList = (List<MccMnc>) mccMncDao.getAllMccMnc();
		bd.setMccMncFK(mcList.get(0));
		return bd;
	}

	private BaseData beforeTheEpoch() {
		sdf.setLenient(false);
		Date date = null;
		try {
			date = sdf.parse("01-01-1969 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BaseData bd = validBaseData();
		bd.setDate(date);
		return bd;
	}

	@Test
	public void testBaseDataIsValidBaseData() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		assertEquals("Not a valid BaseData record", true,
				validator.isValid(validBaseData()));
	}

	@Test(expected = ParseException.class)
	public void testBaseDataDateFormatIsInvalid() throws ParseException {
		String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdtf = new SimpleDateFormat(DATE_TIME_FORMAT);
		sdtf.setLenient(false);

		String aDate = "01-00-2015 00:00:00";

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Date d = sdtf.parse(aDate);
		cal.setTime(d);
	}

	@Test
	public void testBaseDataDateIsBeforeTheEpoch() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		assertEquals("Date is before the Epoch", false,
				validator.isValid(beforeTheEpoch()));
	}

	@Test
	public void cellIdBaseDataIsNegative() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setCellId(-1);
		assertEquals("Cell id is negative", false, validator.isValid(bd));
	}

	@Test
	public void durationBaseDataIsNegative() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setDuration(-1);
		assertEquals("Duration is negative", false, validator.isValid(bd));
	}

	@Test
	public void neVersionBaseDataIsNull() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setNeVersion(null);
		assertEquals("NE version is null", false, validator.isValid(bd));
	}

	@Test
	public void neVersionBaseDataDoesNotMatchFormat() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setNeVersion("1B");
		assertEquals("Format of NE version is incorrect", false,
				validator.isValid(bd));
		BaseData bd2 = validBaseData();
		bd2.setNeVersion("111");
		assertEquals("Format of NE version is incorrect", false,
				validator.isValid(bd2));
		BaseData bd3 = validBaseData();
		bd3.setNeVersion("B11");
		assertEquals("Format of NE version is incorrect", false,
				validator.isValid(bd3));
		BaseData bd4 = validBaseData();
		bd4.setNeVersion("00A");
		assertEquals("Format of NE version is incorrect", false,
				validator.isValid(bd4));
		BaseData bd5 = validBaseData();
		bd5.setNeVersion("01A");
		assertEquals("Format of NE version is incorrect", false,
				validator.isValid(bd5));
		BaseData bd6 = validBaseData();
		bd6.setNeVersion("10A");
		assertEquals("Format of NE version is incorrect", false,
				validator.isValid(bd6));
		BaseData bd7 = validBaseData();
		bd7.setNeVersion("A1B");
		assertEquals("Format of NE version is incorrect", false,
				validator.isValid(bd7));
		BaseData bd8 = validBaseData();
		bd8.setNeVersion("15A");
		assertEquals("Format of NE version is incorrect", true,
				validator.isValid(bd8));
	}

	@Test
	public void imsiBaseDataIsNegative() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setImsi(-1L);
		assertEquals("IMSI is negative", false, validator.isValid(bd));
	}

	@Test
	public void imsiBaseDataIsNotCorrectLength() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setImsi(11111111111114L);
		assertEquals("IMSI is not the correct length", false,
				validator.isValid(bd));
		BaseData bd2 = validBaseData();
		bd2.setImsi(1111111111111116L);
		assertEquals("IMSI is not the correct length", false,
				validator.isValid(bd2));
		BaseData bd3 = validBaseData();
		bd3.setImsi(111111111111115L);
		assertEquals("IMSI is not the correct length", true,
				validator.isValid(bd3));
	}

	@Test
	public void hierBaseDataAreNull() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setHier3Id(null);
		assertEquals("HIER is null", false, validator.isValid(bd));
		BaseData bd2 = validBaseData();
		bd2.setHier32Id(null);
		assertEquals("HIER is null", false, validator.isValid(bd2));
		BaseData bd3 = validBaseData();
		bd3.setHier321Id(null);
		assertEquals("HIER is null", false, validator.isValid(bd3));
	}

	@Test
	public void testEvevntCauseEventIdIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		EventCause ev = anEventCauseRecord();
		ev.setEventId(-1);
		assertEquals("EventId is negative", false, validator.isValid(ev));
	}

	@Test
	public void testEventCauseCauseCodeIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		EventCause ev = anEventCauseRecord();
		ev.setCauseCode(-1);
		assertEquals("Event cause code is negative", false,
				validator.isValid(ev));
	}

	@Test
	public void testEventCauseDescriptionIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		EventCause ev = anEventCauseRecord();
		ev.setDescription(null);
		assertEquals("Event cause description is null", false,
				validator.isValid(ev));
	}

	@Test
	public void testFailureClassCodeIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		FailureClass fc = aFailureClassRecord();
		fc.setFailureClass(-1);
		assertEquals("Failure class is negative", false, validator.isValid(fc));
	}

	@Test
	public void testFailureClassDescriptionIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		FailureClass fc = aFailureClassRecord();
		fc.setDescription(null);
		assertEquals("Failure class description is null", false,
				validator.isValid(fc));
	}

	@Test
	public void testMccMncMccIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = anMccMncRecord();
		mc.setMcc(-1);
		assertEquals("Mcc is negative", false, validator.isValid(mc));
	}

	@Test
	public void testMccMncMncIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = anMccMncRecord();
		mc.setMnc(-1);
		assertEquals("Mnc is negative", false, validator.isValid(mc));
	}

	@Test
	public void testMccMncCountryIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = anMccMncRecord();
		mc.setCountry(null);
		assertEquals("Country is null", false, validator.isValid(mc));
	}

	@Test
	public void testMccMncOperatorIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = anMccMncRecord();
		mc.setOperator(null);
		assertEquals("Operator is null", false, validator.isValid(mc));
	}

	@Test
	public void testUETacIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		UE ue = aUeRecord();
		ue.setTac(-1);
		assertEquals(false, validator.isValid(ue));
	}

	@Test
	public void testUEStringsAreValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		UE ue = aUeRecord();
		UE ue1 = aUeRecord();
		UE ue2 = aUeRecord();
		ue.setManufacturer(null);
		assertEquals("Manufacturer is null", false, validator.isValid(ue));
		ue1.setMarketingName(null);
		assertEquals("Marketing name is null", false, validator.isValid(ue1));
		ue2.setAccessCapability(null);
		assertEquals("Access capability is null", false, validator.isValid(ue2));
	}

}
