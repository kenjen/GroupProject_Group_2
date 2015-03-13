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

import com.project.dao.LookUpDataDAO;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;
import com.project.reader.ReadLookup;

@Stateless
@Local
public class ExcelLookupDataRead implements ReadLookup {

	private static List<EventCause> eventCauseListTop = new ArrayList<EventCause>();
	EventCause eventCauseRecordTop = null;

	private static List<FailureClass> failureClassListTop = new ArrayList<FailureClass>();
	FailureClass failureClassRecordTop = null;

	private static List<UE> ueListTop = new ArrayList<UE>();
	UE ueRecordTop = null;

	private static List<MccMnc> mccMncListTop = new ArrayList<MccMnc>();
	MccMnc mccMncRecordTop = null;

	public static EventCause getEventCause(Integer causeCode, Integer eventId) {
		for (EventCause ec : eventCauseListTop) {
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
		for (FailureClass fc : failureClassListTop) {
			if (fc.getFailureClass().equals(failureCode))
				return fc;
		}
		FailureClass blankFc = new FailureClass(-1, "Empty");
		blankFc.setId(-1);
		return blankFc;
	}

	public static UE getUe(int tac) {
		for (UE ue : ueListTop) {
			if (ue.getTac().equals(tac))
				return ue;
		}
		UE blankUe = new UE(-1, "Empty", "Empty", "Empty");
		blankUe.setId(-1);
		return blankUe;
	}

	public static MccMnc getMccMnc(int mcc, int mnc) {
		for (MccMnc mc : mccMncListTop) {
			if (mc.getMcc().equals(mcc) && mc.getMnc().equals(mnc))
				return mc;
		}
		MccMnc blankMc = new MccMnc(-1, -1, "Empty", "Empty");
		blankMc.setId(-1);
		return blankMc;
	}

	/*
	 * LOOKUP READ + VALIDATION
	 */

	private String inputFile;

	//@Inject
	private ExcellValidator validator = new ExcellValidator();

	private LookUpDataDAO lookUpDao;

	public void setLookUpDao(LookUpDataDAO dao) {
		this.lookUpDao = dao;
	}

	public ExcelLookupDataRead() {
	}

	@Override
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	@Override
	public void setSheetNumber(int sheetNumber) {
	}

	@Override
	public void read() throws IOException {
		eventCauseListTop = new ArrayList<EventCause>();
		failureClassListTop = new ArrayList<FailureClass>();
		ueListTop = new ArrayList<UE>();
		mccMncListTop = new ArrayList<MccMnc>();
		
		eventCauseRecordTop = null;
		failureClassRecordTop = null;
		ueRecordTop = null;
		mccMncRecordTop = null;

		FileInputStream hssfInputWorkbook = new FileInputStream(new File(
				inputFile));
		HSSFWorkbook hssfWorkBook = new HSSFWorkbook(hssfInputWorkbook);
		new DataFormatter();

		// the entire workbook
		for (int i = 1; i < hssfWorkBook.getNumberOfSheets(); i++) {
			HSSFSheet hssfWorkBookSheet = hssfWorkBook.getSheetAt(i);
			// event_cause
			if (i == 1) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					eventCauseRecordTop = new EventCause();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								eventCauseRecordTop.setCauseCode((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								eventCauseRecordTop.setEventId((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 2) {
								eventCauseRecordTop.setDescription(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(eventCauseRecordTop)) {
						eventCauseListTop.add(eventCauseRecordTop);
					} else {
						// TO ERROR ENTITY??
					}
				}
				lookUpDao.addAllEventCause(eventCauseListTop);
			}
			// failure_class
			if (i == 2) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					failureClassRecordTop = new FailureClass();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								failureClassRecordTop
										.setFailureClass((int) cell
												.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								failureClassRecordTop.setDescription(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(failureClassRecordTop)) {
						failureClassListTop.add(failureClassRecordTop);
					} else {
						// TO ERROR ENTITY??
					}
				}
				lookUpDao.addAllFailureClass(failureClassListTop);
			}
			// ue
			if (i == 3) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					ueRecordTop = new UE();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								ueRecordTop.setTac((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								ueRecordTop.setMarketingName(cell
										.getStringCellValue());
							}
							if (cell.getColumnIndex() == 2) {
								ueRecordTop.setManufacturer(cell
										.getStringCellValue());
							}
							if (cell.getColumnIndex() == 3) {
								ueRecordTop.setAccessCapability(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(ueRecordTop)) {
						ueListTop.add(ueRecordTop);
					} else {
						// TO ERROR ENTITY??
					}
				}
				lookUpDao.addAllUe(ueListTop);
			}
			// mcc_mnc
			if (i == 4) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					mccMncRecordTop = new MccMnc();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					boolean rowValid = true;
					for (Cell cell : row) {
						try {
							if (cell.getColumnIndex() == 0) {
								mccMncRecordTop.setMcc((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 1) {
								mccMncRecordTop.setMnc((int) cell
										.getNumericCellValue());
							}
							if (cell.getColumnIndex() == 2) {
								mccMncRecordTop.setCountry(cell
										.getStringCellValue());
							}
							if (cell.getColumnIndex() == 3) {
								mccMncRecordTop.setOperator(cell
										.getStringCellValue());
							}
						} catch (Exception e) {
							rowValid = false;
							break;
						}
					}
					if (rowValid && validator.isValid(mccMncRecordTop)) {
						mccMncListTop.add(mccMncRecordTop);
					} else {
						// TO ERROR ENTITY??
					}
				}
				lookUpDao.addAllMccMnc(mccMncListTop);
			}
		}
		hssfInputWorkbook.close();
		hssfWorkBook.close();
	}
}
