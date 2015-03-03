package com.project.validator;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;
import com.project.reader.excel.ExcellValidator;

public class ExcellValidatorTest {

	private BaseData validBaseData() {
		String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdtf = new SimpleDateFormat(DATE_TIME_FORMAT);
		sdtf.setLenient(false);
		BaseData bd = new BaseData(new Date(), 4195, 1, 101000, 1, 244, 4,
				1000, 16, "13B", 111111111111115L, "123", "123", "123");

		String aDate = "01-01-2015 00:00:00";

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		try {
			Date d = sdtf.parse(aDate);
			cal.setTime(d);
			bd.setDate(d);
		} catch (Exception e) {
			e.getMessage();
		}
		return bd;
	}

	private BaseData beforeTheEpoch() {
		String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdtf = new SimpleDateFormat(DATE_TIME_FORMAT);
		sdtf.setLenient(false);

		String aDate = "01-01-1969 00:00:00";

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		try {
			Date d = sdtf.parse(aDate);
			cal.setTime(d);
		} catch (Exception e) {
			e.getMessage();
		}
		BaseData bd = new BaseData(cal.getTime(), 4195, 1, 101000, 1, 244, 4,
				1000, 16, "13B", 111111111111115L, "123", "123", "123");
		return bd;
	}

	private BaseData dateInTheFuture() {
		String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdtf = new SimpleDateFormat(DATE_TIME_FORMAT);
		sdtf.setLenient(false);

		String aDate = "01-01-2016 00:00:00";

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		try {
			Date d = sdtf.parse(aDate);
			cal.setTime(d);
		} catch (Exception e) {
			e.getMessage();
		}
		BaseData bd = new BaseData(cal.getTime(), 4195, 1, 101000, 1, 244, 4,
				1000, 16, "13B", 111111111111115L, "123", "123", "123");
		return bd;
	}

	@Test
	public void testBaseDataIsValidBaseData() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		assertEquals(true, validator.isValid(validBaseData()));
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
		assertEquals(false, validator.isValid(beforeTheEpoch()));
	}

	@Test
	public void testBaseDataDateIsNotInTheFuture() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		assertEquals(false, validator.isValid(dateInTheFuture()));
	}

	@Test
	public void testBaseDataEventIdCauseCodeAreNegative() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setEventId(-1);
		assertEquals(false, validator.isValid(bd));
		BaseData bd2 = validBaseData();
		bd2.setCauseCode(-1);
		assertEquals(false, validator.isValid(bd2));
	}

	@Test
	public void cellIdBaseDataIsNegative() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setCellId(-1);
		assertEquals(false, validator.isValid(bd));
	}

	@Test
	public void durationBaseDataIsNegative() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setDuration(-1);
		assertEquals(false, validator.isValid(bd));
	}

	@Test
	public void neVersionBaseDataIsNull() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setNeVersion(null);
		assertEquals(false, validator.isValid(bd));
	}

	@Test
	public void neVersionBaseDataDoesNotMatchFormat() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setNeVersion("1B");
		assertEquals(false, validator.isValid(bd));
		BaseData bd2 = validBaseData();
		bd2.setNeVersion("111");
		assertEquals(false, validator.isValid(bd2));
		BaseData bd3 = validBaseData();
		bd3.setNeVersion("B11");
		assertEquals(false, validator.isValid(bd3));
		BaseData bd4 = validBaseData();
		bd4.setNeVersion("00A");
		assertEquals(false, validator.isValid(bd4));
		BaseData bd5 = validBaseData();
		bd5.setNeVersion("01A");
		assertEquals(false, validator.isValid(bd5));
		BaseData bd6 = validBaseData();
		bd6.setNeVersion("10A");
		assertEquals(false, validator.isValid(bd6));
		BaseData bd7 = validBaseData();
		bd7.setNeVersion("A1B");
		assertEquals(false, validator.isValid(bd7));
		BaseData bd8 = validBaseData();
		bd8.setNeVersion("15A");
		assertEquals(true, validator.isValid(bd8));
	}

	@Test
	public void imsiBaseDataIsNegative() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setImsi(-1L);
		assertEquals(false, validator.isValid(bd));
	}

	@Test
	public void imsiBaseDataIsNotCorrectLength() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setImsi(11111111111114L);
		assertEquals(false, validator.isValid(bd));
		BaseData bd2 = validBaseData();
		bd2.setImsi(1111111111111116L);
		assertEquals(false, validator.isValid(bd2));
		BaseData bd3 = validBaseData();
		bd3.setImsi(111111111111115L);
		assertEquals(true, validator.isValid(bd3));
	}

	@Test
	public void hierBaseDataAreNull() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		BaseData bd = validBaseData();
		bd.setHier3Id(null);
		assertEquals(false, validator.isValid(bd));
		BaseData bd2 = validBaseData();
		bd2.setHier32Id(null);
		assertEquals(false, validator.isValid(bd2));
		BaseData bd3 = validBaseData();
		bd3.setHier321Id(null);
		assertEquals(false, validator.isValid(bd3));
	}

	private EventCause validEventCause() {
		EventCause eventCause = new EventCause(10, 4095, "test");
		return eventCause;
	}

	@Test
	public void testEvevntCauseEventIdIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		EventCause ev = validEventCause();
		ev.setEventId(-1);
		assertEquals(false, validator.isValid(ev));
	}

	@Test
	public void testEventCauseCauseCodeIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		EventCause ev = validEventCause();
		ev.setCauseCode(-1);
		assertEquals(false, validator.isValid(ev));
	}

	@Test
	public void testEventCauseDescriptionIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		EventCause ev = validEventCause();
		ev.setDescription(null);
		assertEquals(false, validator.isValid(ev));
	}

	private FailureClass validFailureClass() {
		FailureClass failureClass = new FailureClass(1, "test");
		return failureClass;
	}

	@Test
	public void testFailureClassCodeIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		FailureClass fc = validFailureClass();
		fc.setFailureClass(-1);
		assertEquals(false, validator.isValid(fc));
	}

	@Test
	public void testFailureClassDescriptionIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		FailureClass fc = validFailureClass();
		fc.setDescription(null);
		assertEquals(false, validator.isValid(fc));
	}

	private MccMnc validMccMnc() {
		MccMnc mccMnc = new MccMnc(200, 10, "test", "test");
		return mccMnc;
	}

	@Test
	public void testMccMncMccIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = validMccMnc();
		mc.setMcc(-1);
		assertEquals(false, validator.isValid(mc));
	}

	@Test
	public void testMccMncMncIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = validMccMnc();
		mc.setMnc(-1);
		assertEquals(false, validator.isValid(mc));
	}

	@Test
	public void testMccMncCountryIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = validMccMnc();
		mc.setMcc(null);
		assertEquals(false, validator.isValid(mc));
	}

	@Test
	public void testMccMncOperatorIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		MccMnc mc = validMccMnc();
		mc.setMcc(null);
		assertEquals(false, validator.isValid(mc));
	}

	public UE validUe() {
		UE ue = new UE(1000, "test", "test", "test");
		return ue;
	}

	@Test
	public void testUETacIsValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		UE ue = validUe();
		ue.setTac(-1);
		assertEquals(false, validator.isValid(ue));
	}

	@Test
	public void testUEStringsAreValid() {
		ExcellValidator validator = new ExcellValidator();
		assertNotNull(validator);
		UE ue = validUe();
		UE ue1 = validUe();
		UE ue2 = validUe();
		ue.setManufacturer(null);
		assertEquals(false, validator.isValid(ue));
		ue1.setMarketingName(null);
		assertEquals(false, validator.isValid(ue1));
		ue2.setAccessCapability(null);
		assertEquals(false, validator.isValid(ue2));
	}

}
