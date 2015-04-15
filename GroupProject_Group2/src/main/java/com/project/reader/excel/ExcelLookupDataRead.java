package com.project.reader.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.dao.LookUpDataDAO;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;
import com.project.reader.ReadLookup;

@Stateless
@Local
public class ExcelLookupDataRead implements ReadLookup {

	private static final Logger log = LoggerFactory.getLogger(ExcelLookupDataRead.class);

	private String inputFile;

	private ExcellValidator validator;

	private LookUpDataDAO lookUpDao;

	private static List<EventCause> dbEventCauseList = new ArrayList<EventCause>();

	private static List<FailureClass> dbFailureClassList = new ArrayList<FailureClass>();

	private static List<UE> dbUeList = new ArrayList<UE>();

	private static List<MccMnc> dbMccMncList = new ArrayList<MccMnc>();

	@Override
	public void setLookUpDao(LookUpDataDAO dao) {
		this.lookUpDao = dao;
	}

	public ExcelLookupDataRead() {
		validator = new ExcellValidator();
	}

	@Override
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public static EventCause getEventCause(Integer causeCode, Integer eventId) {
		for (EventCause ec : dbEventCauseList) {
			if (ec.getCauseCode().equals(causeCode)
					&& ec.getEventId().equals(eventId)) {
				return ec;
			}
		}
		EventCause blankEc = new EventCause(-1, -1, "Empty");
		blankEc.setId(-1);
		return blankEc;
	}

	public static FailureClass getFailureClass(int failureCode) {
		for (FailureClass fc : dbFailureClassList) {
			if (fc.getFailureClass().equals(failureCode))
				return fc;
		}
		FailureClass blankFc = new FailureClass(-1, "Empty");
		blankFc.setId(-1);
		return blankFc;
	}

	public static UE getUe(int tac) {
		for (UE ue : dbUeList) {
			if (ue.getTac().equals(tac))
				return ue;
		}
		UE blankUe = new UE(-1, "Empty", "Empty", "Empty");
		blankUe.setId(-1);
		return blankUe;
	}

	public static MccMnc getMccMnc(int mcc, int mnc) {
		for (MccMnc mc : dbMccMncList) {
			if (mc.getMcc().equals(mcc) && mc.getMnc().equals(mnc))
				return mc;
		}
		MccMnc blankMc = new MccMnc(-1, -1, "Empty", "Empty");
		blankMc.setId(-1);
		return blankMc;
	}

	@Override
	public void read() throws IOException {
		ArrayList<EventCause> eventCauseList = null;
		ArrayList<FailureClass> failureClassList = null;
		ArrayList<UE> ueList = null;
		ArrayList<MccMnc> mccMncList = null;

		EventCause eventCauseRecord = null;
		FailureClass failureClassRecord = null;
		UE ueRecord = null;
		MccMnc mccMncRecord = null;

		FileInputStream hssfInputWorkbook = new FileInputStream(new File(
				inputFile));
		
		log.info("input file path = " + inputFile);
		
		HSSFWorkbook hssfWorkBook = new HSSFWorkbook(hssfInputWorkbook);
		new DataFormatter();

		// the entire workbook
		for (int i = 1; i < hssfWorkBook.getNumberOfSheets(); i++) {
			HSSFSheet hssfWorkBookSheet = hssfWorkBook.getSheetAt(i);
			// event_cause
			if (i == 1) {
				eventCauseList = new ArrayList<EventCause>();
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					eventCauseRecord = new EventCause();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								eventCauseRecord.setCauseCode((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								eventCauseRecord.setEventId((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 2) {
								eventCauseRecord.setDescription(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(eventCauseRecord)) {
						eventCauseList.add(eventCauseRecord);
					} else {
						// TO ERROR ENTITY??
					}
				}
				dbEventCauseList.addAll(eventCauseList);
				lookUpDao.addAllEventCause(eventCauseList);
			}
			// failure_class
			if (i == 2) {
				failureClassList = new ArrayList<FailureClass>();
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					failureClassRecord = new FailureClass();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								failureClassRecord.setFailureClass((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								failureClassRecord.setDescription(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(failureClassRecord)) {
						failureClassList.add(failureClassRecord);
					} else {
						// TO ERROR ENTITY??
					}
				}
				dbFailureClassList.addAll(failureClassList);
				lookUpDao.addAllFailureClass(failureClassList);
			}
			// ue
			if (i == 3) {
				ueList = new ArrayList<UE>();
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					ueRecord = new UE();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								ueRecord.setTac((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								ueRecord.setMarketingName(cell
										.getStringCellValue());
							}
							if (cell.getColumnIndex() == 2) {
								ueRecord.setManufacturer(cell
										.getStringCellValue());
							}
							if (cell.getColumnIndex() == 3) {
								ueRecord.setAccessCapability(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(ueRecord)) {
						ueList.add(ueRecord);
					} else {
						// TO ERROR ENTITY??
					}
				}
				dbUeList.addAll(ueList);
				lookUpDao.addAllUe(ueList);
			}
			// mcc_mnc
			if (i == 4) {
				mccMncList = new ArrayList<MccMnc>();
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					mccMncRecord = new MccMnc();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								mccMncRecord.setMcc((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								mccMncRecord.setMnc((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 2) {
								mccMncRecord.setCountry(cell
										.getStringCellValue());
							}
							if (cell.getColumnIndex() == 3) {
								mccMncRecord.setOperator(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(mccMncRecord)) {
						mccMncList.add(mccMncRecord);
					} else {
						// TO ERROR ENTITY??
					}
				}
				dbMccMncList.addAll(mccMncList);
				lookUpDao.addAllMccMnc(mccMncList);
			}
		}
		hssfInputWorkbook.close();
		hssfWorkBook.close();
	}
}
