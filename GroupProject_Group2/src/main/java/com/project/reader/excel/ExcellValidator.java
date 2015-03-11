package com.project.reader.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;
import com.project.reader.Validator;

public class ExcellValidator implements Validator {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(ExcellValidator.class);

	/*
	 * VALID DATE FORMAT
	 */
	private final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	/*
	 * VAILD NE VERSION
	 */
	private final String NE_VERSION_PATTERN = ("^([1-9][1-9][A-B])$");

	@Override
	public boolean isValid(BaseData baseData) {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setLenient(false);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String dateTime = sdf.format(baseData.getDate());
		if (baseData.getDate() == null)
			return false;
		try {
			Date date = sdf.parse(dateTime);
			cal.setTime(date);
		} catch (ParseException e) {
			return false;
		}
		if (baseData.getDate().getTime() < 0)
			return false; // before the Epoch
			// if (baseData.getEventId() < 0 || baseData.getCauseCode() < 0)
			// return false;
		/*logger.info("BaseData object " + baseData.getId() + "EventCauseFK: "
				+ baseData.getEventCauseFK());*/
		if (baseData.getEventCauseFK().getId().equals(-1))
			return false;
		// if (baseData.getFailureClass() < 0)
		// return false;
		if (baseData.getFaliureClassFK().getId().equals(-1))
			return false;
		// if (baseData.getTac() < 0)
		// return false;
		if (baseData.getUeFK().getId().equals(-1))
			return false;
		// if (baseData.getMcc() < 0 || baseData.getMnc() < 0)
		// return false;
		if (baseData.getMccMncFK().getId().equals(-1))
			return false;
		if (baseData.getCellId() < 0)
			return false;
		if (baseData.getDuration() < 0)
			return false;
		if (baseData.getNeVersion() == null)
			return false;
		if (!baseData.getNeVersion().matches(NE_VERSION_PATTERN))
			return false;
		if (baseData.getImsi() < 0)
			return false;
		if (baseData.getImsi().toString().length() != 15)
			return false;
		if (baseData.getHier3Id() == null || baseData.getHier32Id() == null
				|| baseData.getHier321Id() == null)
			return false;
		/*
		 * Return true if validation passed
		 */
		return true;
	}

	@Override
	public boolean isValid(EventCause eventCause) {
		if (eventCause.getCauseCode() < 0)
			return false;
		if (eventCause.getEventId() < 0)
			return false;
		if (eventCause.getDescription() == null)
			return false;
		return true;
	}

	@Override
	public boolean isValid(FailureClass failureClass) {
		if (failureClass.getFailureClass() < 0)
			return false;
		if (failureClass.getDescription() == null)
			return false;
		return true;
	}

	@Override
	public boolean isValid(MccMnc mccMnc) {
		if (mccMnc.getMcc() < 0)
			return false;
		if (mccMnc.getMnc() < 0)
			return false;
		if (mccMnc.getCountry() == null)
			return false;
		if (mccMnc.getOperator() == null)
			return false;
		return true;
	}

	@Override
	public boolean isValid(UE ue) {
		if (ue.getTac() < 0)
			return false;
		if (ue.getMarketingName() == null)
			return false;
		if (ue.getManufacturer() == null)
			return false;
		if (ue.getAccessCapability() == null)
			return false;
		return true;
	}

}
