package com.project.rest;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.MccMnc;
import com.project.entities.UE;

@RunWith(Arquillian.class)
public class BaseDataRestTest {
	
	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

	private static final Logger log = LoggerFactory.getLogger(BaseDataRestTest.class);

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
		EventCause eventCauseRecord = new EventCause();
		eventCauseRecord.setCauseCode(10);
		eventCauseRecord.setEventId(1);
		eventCauseRecord.setDescription("TestEvent");

		em.persist(eventCauseRecord);
		tx.commit();
		em.clear();
		
		tx.begin();
		em.joinTransaction();
		UE ueRecord = new UE(808, "marketingName", "manufacturer", "accessCapability");
		em.persist(ueRecord);
		tx.commit();
		em.clear();

		tx.begin();
		em.joinTransaction();

		MccMnc mccMncRecordA = new MccMnc();
		mccMncRecordA.setMcc(1);
		mccMncRecordA.setMnc(10);
		mccMncRecordA.setCountry("TestCountry A");
		mccMncRecordA.setOperator("TestOperator A");
		em.persist(mccMncRecordA);
		MccMnc mccMncRecordB = new MccMnc();
		mccMncRecordB.setMcc(12);
		mccMncRecordB.setMnc(10);
		mccMncRecordB.setCountry("TestCountry B");
		mccMncRecordB.setOperator("TestOperator B");
		em.persist(mccMncRecordB);
		MccMnc mccMncRecordC = new MccMnc();
		mccMncRecordC.setMcc(1);
		mccMncRecordC.setMnc(14);
		mccMncRecordC.setCountry("TestCountry C");
		mccMncRecordC.setOperator("TestOperator C");
		em.persist(mccMncRecordC);
		MccMnc mccMncRecordD = new MccMnc();
		mccMncRecordD.setMcc(12);
		mccMncRecordD.setMnc(14);
		mccMncRecordD.setCountry("TestCountry D");
		mccMncRecordD.setOperator("TestOperator D");
		em.persist(mccMncRecordD);
		
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
		baseDataRecord.setUeFK(ueRecord);
		baseDataRecord.setCellId(5);
		baseDataRecord.setEventCauseFK(eventCauseRecord);
		baseDataRecord.setMccMncFK(mccMncRecordA);
		em.persist(baseDataRecord);
		
		MccMnc current = mccMncRecordB;
		for(int i=2; i<12; i++){
			if(i==4){
				current = mccMncRecordC;
			}else if(i==7){
				current = mccMncRecordD;
			}
			Long imsi = (long) (991+i);
			String month;
			if(i<10){
				month = "0" + i;
			}else{
				month = i+"";
			}
			BaseData b = getNewBaseData(imsi, "10-"+month+"-2013 09:15:00", eventCauseRecord, current, ueRecord);
			em.persist(b);
			b = null;
		}
		
		tx.commit();
		em.clear();
		
		eventCauseRecord = null;
		mccMncRecordA = null;
		mccMncRecordB = null;
		mccMncRecordC = null;
		mccMncRecordD = null;
		current = null;
		baseDataRecord = null;
	}
	
	private BaseData getNewBaseData(Long imsi, String dateStr, EventCause eventCauseRecord, MccMnc mccMncRecord, UE ueRecord) throws ParseException{
		Date date = sdf.parse(dateStr);
		BaseData baseDataRecord2 = new BaseData();
		baseDataRecord2.setDate(date);
		baseDataRecord2.setDuration(1000);
		baseDataRecord2.setImsi(imsi);
		baseDataRecord2.setMcc(1);
		baseDataRecord2.setMnc(10);
		baseDataRecord2.setCellId(5);
		baseDataRecord2.setUeFK(ueRecord);
		baseDataRecord2.setEventCauseFK(eventCauseRecord);
		baseDataRecord2.setMccMncFK(mccMncRecord);
		return baseDataRecord2;
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
	public void testGetAllUploadedFilePaths(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<BaseData> baseData = baseDataRest.getAllBaseData();
		
		assertEquals(11, baseData.size());
		assertEquals(sdf.parse("10-10-2013 09:00:00").getTime(), baseData.get(0).getDate().getTime());
		assertEquals(1000, (int)baseData.get(0).getDuration());
		assertEquals(1001L, (long)baseData.get(0).getImsi());
		assertEquals(1, (int)baseData.get(0).getMccMncFK().getMcc());
		assertEquals(10, (int)baseData.get(0).getMccMncFK().getMnc());
		assertEquals(1001L, (long)baseData.get(0).getImsi());
		assertEquals("TestEvent", baseData.get(0).getEventCauseFK().getDescription());
		
		baseData = null;
	}
	
	@Test
	public void testGetAllEventCause(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<EventCause> eventCauses = (List<EventCause>) baseDataRest.getAllEventCause();
		
		assertEquals(11, eventCauses.size());
		assertEquals(10, (int)eventCauses.get(0).getCauseCode());
		assertEquals(1, (int)eventCauses.get(0).getEventId());
		assertEquals("TestEvent", eventCauses.get(0).getDescription());
		
		eventCauses = null;
	}
	
	@Test
	public void testGetAllEventCauseByImsi(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<EventCause> eventCauses = (List<EventCause>) baseDataRest.getAllEventCause(1001L);
		
		assertEquals(2, eventCauses.size());
		assertEquals(10, (int)eventCauses.get(0).getCauseCode());
		assertEquals(1, (int)eventCauses.get(0).getEventId());
		assertEquals("TestEvent", eventCauses.get(0).getDescription());
		
		List<EventCause> eventCausesEmpty = (List<EventCause>) baseDataRest.getAllEventCause(900L);
		
		assertEquals(0, eventCausesEmpty.size());
		
		eventCauses = null;
		eventCausesEmpty = null;
	}
	
	@Test
	public void testCountImsiByDateRange(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<String[]> baseDataAsString = baseDataRest.getImsiByDateRange("c002013-10-10T08:50:502013-10-10T09:10:10");
		
		assertEquals(1, baseDataAsString.size());
		assertEquals(22, baseDataAsString.get(0).length);
		assertEquals("", baseDataAsString.get(0)[0]);
		assertEquals("", baseDataAsString.get(0)[1]);
		assertEquals("", baseDataAsString.get(0)[2]);
		//assertEquals(baseDataRecord.getDate().toString(), baseDataAsString.get(0)[3]);
		assertEquals("", baseDataAsString.get(0)[4]);
		assertEquals("", baseDataAsString.get(0)[5]);
		assertEquals("", baseDataAsString.get(0)[6]);
		assertEquals("1001", baseDataAsString.get(0)[7]);
		assertEquals("", baseDataAsString.get(0)[8]);
		assertEquals("", baseDataAsString.get(0)[9]);
		assertEquals("", baseDataAsString.get(0)[10]);
		assertEquals("", baseDataAsString.get(0)[11]);
		assertEquals("", baseDataAsString.get(0)[12]);
		assertEquals("", baseDataAsString.get(0)[13]);
		assertEquals("", baseDataAsString.get(0)[14]);
		assertEquals("", baseDataAsString.get(0)[15]);
		assertEquals("", baseDataAsString.get(0)[16]);
		assertEquals("", baseDataAsString.get(0)[17]);
		assertEquals("", baseDataAsString.get(0)[18]);
		assertEquals("", baseDataAsString.get(0)[19]);
		assertEquals("", baseDataAsString.get(0)[20]);
		assertEquals("", baseDataAsString.get(0)[21]);
		
		List<String[]> baseDataAsStringEmpty = baseDataRest.getImsiByDateRange("c002014-10-10T08:50:502014-10-10T09:10:10");
		
		assertEquals(0, baseDataAsStringEmpty.size());
		
		baseDataAsString = null;
		baseDataAsStringEmpty = null;
	}
	
	@Test
	public void testGetCountImsiByDateRange(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<String[]> baseDataAsString = baseDataRest.getCountImsiByDateRange("c002013-10-10T08:50:502013-10-10T09:10:10");
		
		assertEquals(1, baseDataAsString.size());
		assertEquals(22, baseDataAsString.get(0).length);
		assertEquals("", baseDataAsString.get(0)[0]);
		assertEquals("", baseDataAsString.get(0)[1]);
		assertEquals("", baseDataAsString.get(0)[2]);
		assertEquals("", baseDataAsString.get(0)[3]);
		assertEquals("1000", baseDataAsString.get(0)[4]);
		assertEquals("", baseDataAsString.get(0)[5]);
		assertEquals("", baseDataAsString.get(0)[6]);
		assertEquals("1001", baseDataAsString.get(0)[7]);
		assertEquals("", baseDataAsString.get(0)[8]);
		assertEquals("", baseDataAsString.get(0)[9]);
		assertEquals("", baseDataAsString.get(0)[10]);
		assertEquals("", baseDataAsString.get(0)[11]);
		assertEquals("", baseDataAsString.get(0)[12]);
		assertEquals("", baseDataAsString.get(0)[13]);
		assertEquals("", baseDataAsString.get(0)[14]);
		assertEquals("1", baseDataAsString.get(0)[15]);
		assertEquals("", baseDataAsString.get(0)[16]);
		assertEquals("", baseDataAsString.get(0)[17]);
		assertEquals("", baseDataAsString.get(0)[18]);
		assertEquals("", baseDataAsString.get(0)[19]);
		assertEquals("", baseDataAsString.get(0)[20]);
		assertEquals("", baseDataAsString.get(0)[21]);
		
		List<String[]> baseDataAsStringEmpty = baseDataRest.getImsiByDateRange("c002014-10-10T08:50:502014-10-10T09:10:10");
		
		assertEquals(0, baseDataAsStringEmpty.size());
		
		baseDataAsString = null;
		baseDataAsStringEmpty = null;
	}
	
	@Test
	public void testGetCountSingleImsiByDateRange(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<String[]> baseDataAsString = baseDataRest.getCountSingleImsiByDateRange("c002013-10-10T08:50:502013-10-10T09:20:101001");
		
		assertEquals(1, baseDataAsString.size());
		assertEquals(22, baseDataAsString.get(0).length);
		assertEquals("", baseDataAsString.get(0)[0]);
		assertEquals("", baseDataAsString.get(0)[1]);
		assertEquals("", baseDataAsString.get(0)[2]);
		assertEquals("", baseDataAsString.get(0)[3]);
		assertEquals("", baseDataAsString.get(0)[4]);
		assertEquals("", baseDataAsString.get(0)[5]);
		assertEquals("", baseDataAsString.get(0)[6]);
		assertEquals("1001", baseDataAsString.get(0)[7]);
		assertEquals("", baseDataAsString.get(0)[8]);
		assertEquals("", baseDataAsString.get(0)[9]);
		assertEquals("", baseDataAsString.get(0)[10]);
		assertEquals("", baseDataAsString.get(0)[11]);
		assertEquals("", baseDataAsString.get(0)[12]);
		assertEquals("", baseDataAsString.get(0)[13]);
		assertEquals("", baseDataAsString.get(0)[14]);
		assertEquals("2", baseDataAsString.get(0)[15]);
		assertEquals("", baseDataAsString.get(0)[16]);
		assertEquals("", baseDataAsString.get(0)[17]);
		assertEquals("", baseDataAsString.get(0)[18]);
		assertEquals("", baseDataAsString.get(0)[19]);
		assertEquals("", baseDataAsString.get(0)[20]);
		assertEquals("", baseDataAsString.get(0)[21]);
		
		List<String[]> baseDataAsStringEmpty = baseDataRest.getCountSingleImsiByDateRange("c002013-10-10T08:50:502013-10-10T09:20:101009");

		assertEquals("null", baseDataAsStringEmpty.get(0)[7]);
		assertEquals("0", baseDataAsStringEmpty.get(0)[15]);
		
		List<String[]> baseDataAsStringSingle = baseDataRest.getCountSingleImsiByDateRange("c002013-10-10T08:50:002013-10-10T09:10:101001");
		
		assertEquals(1, baseDataAsStringSingle.size());
		
		baseDataAsString = null;
		baseDataAsStringEmpty = null;
		baseDataAsStringSingle = null;
	}
	
	@Test
	public void testGetCountTop10ImsiByDateRange(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<String[]> baseDataAsString = baseDataRest.getCountTop10ImsiByDateRange("c002013-01-10T08:50:502013-12-10T09:20:10");
		
		assertEquals(10, baseDataAsString.size());
		assertEquals(22, baseDataAsString.get(0).length);
		
		assertEquals("", baseDataAsString.get(0)[0]);
		assertEquals("", baseDataAsString.get(0)[1]);
		assertEquals("", baseDataAsString.get(0)[2]);
		assertEquals("", baseDataAsString.get(0)[3]);
		assertEquals("", baseDataAsString.get(0)[4]);
		assertEquals("", baseDataAsString.get(0)[5]);
		assertEquals("", baseDataAsString.get(0)[6]);
		assertEquals("1001", baseDataAsString.get(0)[7]);
		assertEquals("", baseDataAsString.get(0)[8]);
		assertEquals("", baseDataAsString.get(0)[9]);
		assertEquals("", baseDataAsString.get(0)[10]);
		assertEquals("", baseDataAsString.get(0)[11]);
		assertEquals("", baseDataAsString.get(0)[12]);
		assertEquals("", baseDataAsString.get(0)[13]);
		assertEquals("", baseDataAsString.get(0)[14]);
		assertEquals("2", baseDataAsString.get(0)[15]);
		assertEquals("", baseDataAsString.get(0)[16]);
		assertEquals("", baseDataAsString.get(0)[17]);
		assertEquals("", baseDataAsString.get(0)[18]);
		assertEquals("", baseDataAsString.get(0)[19]);
		assertEquals("", baseDataAsString.get(0)[20]);
		assertEquals("", baseDataAsString.get(0)[21]);
		
		assertEquals("993", baseDataAsString.get(1)[7]);
		assertEquals("1", baseDataAsString.get(1)[15]);
		assertEquals("994", baseDataAsString.get(2)[7]);
		assertEquals("1", baseDataAsString.get(2)[15]);
		assertEquals("995", baseDataAsString.get(3)[7]);
		assertEquals("1", baseDataAsString.get(3)[15]);
		assertEquals("996", baseDataAsString.get(4)[7]);
		assertEquals("1", baseDataAsString.get(4)[15]);
		assertEquals("997", baseDataAsString.get(5)[7]);
		assertEquals("1", baseDataAsString.get(5)[15]);
		assertEquals("998", baseDataAsString.get(6)[7]);
		assertEquals("1", baseDataAsString.get(6)[15]);
		assertEquals("999", baseDataAsString.get(7)[7]);
		assertEquals("1", baseDataAsString.get(7)[15]);
		assertEquals("1000", baseDataAsString.get(8)[7]);
		assertEquals("1", baseDataAsString.get(8)[15]);
		assertEquals("1002", baseDataAsString.get(9)[7]);
		assertEquals("1", baseDataAsString.get(9)[15]);
		
		baseDataAsString = null;
	}
	
	@Test
	public void testGetCountTop10ComboBetweenDates(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<String[]> baseDataAsString = baseDataRest.getCountTop10ComboBetweenDates("c002013-01-10T08:50:502013-12-10T09:20:10");
		
		assertEquals(4, baseDataAsString.size());
		assertEquals(22, baseDataAsString.get(0).length);
		
		assertEquals("", baseDataAsString.get(0)[0]);
		assertEquals("", baseDataAsString.get(0)[1]);
		assertEquals("5", baseDataAsString.get(0)[2]);
		assertEquals("", baseDataAsString.get(0)[3]);
		assertEquals("", baseDataAsString.get(0)[4]);
		assertEquals("", baseDataAsString.get(0)[5]);
		assertEquals("", baseDataAsString.get(0)[6]);
		assertEquals("", baseDataAsString.get(0)[7]);
		assertEquals("", baseDataAsString.get(0)[8]);
		assertEquals("", baseDataAsString.get(0)[9]);
		assertEquals("", baseDataAsString.get(0)[10]);
		assertEquals("", baseDataAsString.get(0)[11]);
		assertEquals("", baseDataAsString.get(0)[12]);
		assertEquals("", baseDataAsString.get(0)[13]);
		assertEquals("", baseDataAsString.get(0)[14]);
		assertEquals("5", baseDataAsString.get(0)[15]);
		assertEquals("TestCountry D", baseDataAsString.get(0)[16]);
		assertEquals("TestOperator D", baseDataAsString.get(0)[17]);
		assertEquals("", baseDataAsString.get(0)[18]);
		assertEquals("", baseDataAsString.get(0)[19]);
		assertEquals("", baseDataAsString.get(0)[20]);
		assertEquals("", baseDataAsString.get(0)[21]);
		
		assertEquals("5", baseDataAsString.get(1)[2]);
		assertEquals("3", baseDataAsString.get(1)[15]);
		assertEquals("TestCountry C", baseDataAsString.get(1)[16]);
		assertEquals("TestOperator C", baseDataAsString.get(1)[17]);
		assertEquals("5", baseDataAsString.get(2)[2]);
		assertEquals("2", baseDataAsString.get(2)[15]);
		assertEquals("TestCountry B", baseDataAsString.get(2)[16]);
		assertEquals("TestOperator B", baseDataAsString.get(2)[17]);
		assertEquals("5", baseDataAsString.get(3)[2]);
		assertEquals("1", baseDataAsString.get(3)[15]);
		assertEquals("TestCountry A", baseDataAsString.get(3)[16]);
		assertEquals("TestOperator A", baseDataAsString.get(3)[17]);
		
		baseDataAsString = null;
	}
	
	@Test
	public void testGetCauseCodeByIMSI(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		
		List<String[]> baseDataAsString = baseDataRest.getCauseCodeByIMSI("c001001");
		
		assertEquals(1, baseDataAsString.size());
		assertEquals(22, baseDataAsString.get(0).length);
		
		assertEquals("", baseDataAsString.get(0)[0]);
		assertEquals("10", baseDataAsString.get(0)[1]);
		assertEquals("", baseDataAsString.get(0)[2]);
		assertEquals("", baseDataAsString.get(0)[3]);
		assertEquals("", baseDataAsString.get(0)[4]);
		assertEquals("", baseDataAsString.get(0)[5]);
		assertEquals("", baseDataAsString.get(0)[6]);
		assertEquals("", baseDataAsString.get(0)[7]);
		assertEquals("", baseDataAsString.get(0)[8]);
		assertEquals("", baseDataAsString.get(0)[9]);
		assertEquals("", baseDataAsString.get(0)[10]);
		assertEquals("", baseDataAsString.get(0)[11]);
		assertEquals("", baseDataAsString.get(0)[12]);
		assertEquals("", baseDataAsString.get(0)[13]);
		assertEquals("", baseDataAsString.get(0)[14]);
		assertEquals("2", baseDataAsString.get(0)[15]);
		assertEquals("", baseDataAsString.get(0)[16]);
		assertEquals("", baseDataAsString.get(0)[17]);
		assertEquals("", baseDataAsString.get(0)[18]);
		assertEquals("", baseDataAsString.get(0)[19]);
		assertEquals("", baseDataAsString.get(0)[20]);
		assertEquals("", baseDataAsString.get(0)[21]);
		
		List<String[]> baseDataAsStringEmpty = baseDataRest.getCauseCodeByIMSI("c000950");
		assertEquals(0, baseDataAsStringEmpty.size());
		
		List<String[]> baseDataAsStringSingle = baseDataRest.getCauseCodeByIMSI("c001000");
		assertEquals(1, baseDataAsStringSingle.size());
		assertEquals("1", baseDataAsStringSingle.get(0)[15]);
		
		baseDataAsString = null;
		baseDataAsStringEmpty = null;
		baseDataAsStringSingle = null;
	}
	
	@Test
	public void testCountCellFailuresByModelEventCause(@ArquillianResteasyResource BaseDataRest baseDataRest){
		String data = "TestEvent::c00marketingName";
		List<String[]> list = baseDataRest.countCellFailuresByModelEventCause(data);
		
		assertEquals(1, list.size());
		assertEquals(3, list.get(0).length);
		
		assertEquals("5", list.get(0)[0]);
		assertEquals("11", list.get(0)[1]);
		assertEquals("11000", list.get(0)[2]);
	}
	
	@Test
	public void testCountAllFailuresByDate(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		String data = "c002013-01-10T08:50:502013-12-10T09:20:10";
		List<String[]> count = baseDataRest.countAllFailuresByDate(data);
		
		assertEquals(1, count.size());
		assertEquals("11", count.get(0)[15]);
	}
}
