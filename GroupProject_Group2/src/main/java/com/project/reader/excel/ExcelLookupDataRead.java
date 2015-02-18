package com.project.reader.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.project.reader.Read;

public class ExcelLookupDataRead implements Read {

	private String inputFile;

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
		FileInputStream hssfInputWorkbook = new FileInputStream(new File(
				inputFile));

		List<EventCause> eventCauseList = new ArrayList<EventCause>();
		EventCause eventCauseRecord = null;

		List<FailureClass> failureClassList = new ArrayList<FailureClass>();
		FailureClass failureClassRecord = null;

		List<UE> ueList = new ArrayList<UE>();
		UE ueRecord = null;

		List<MccMnc> mccMncList = new ArrayList<MccMnc>();
		MccMnc mccMncRecord = null;

		HSSFWorkbook hssfWorkBook = new HSSFWorkbook(hssfInputWorkbook);

		DataFormatter df = new DataFormatter();
		// the entire workbook
		for (int i = 1; i < hssfWorkBook.getNumberOfSheets(); i++) {
			HSSFSheet hssfWorkBookSheet = hssfWorkBook.getSheetAt(i);
			// event_cause
			if (i == 1) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					eventCauseRecord = new EventCause();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					for (Cell cell : row) {
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
						eventCauseList.add(eventCauseRecord);
					}
				}
				lookUpDao.addAllEventCause(eventCauseList);
			}
			// failure_class
			if (i == 2) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					failureClassRecord = new FailureClass();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					for (Cell cell : row) {
						if (cell.getColumnIndex() == 0) {
							failureClassRecord.setFailureClass((int) cell
									.getNumericCellValue());
						}
						if (cell.getColumnIndex() == 1) {
							failureClassRecord.setDescription(cell
									.getStringCellValue());
						}
						failureClassList.add(failureClassRecord);
					}
				}
				lookUpDao.addAllFailureClass(failureClassList);
			}
			// ue
			if (i == 3) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					ueRecord = new UE();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					for (Cell cell : row) {
						if (cell.getColumnIndex() == 0) {
							ueRecord.setTac((int) cell.getNumericCellValue());
						}
						if (cell.getColumnIndex() == 1) {
							ueRecord.setMarketingName(cell.getStringCellValue());
						}
						if (cell.getColumnIndex() == 2) {
							ueRecord.setManufacturer(cell.getStringCellValue());
						}
						if (cell.getColumnIndex() == 3) {
							ueRecord.setAccessCapability(cell
									.getStringCellValue());
						}
						ueList.add(ueRecord);
					}
				}
				lookUpDao.addAllUe(ueList);
			}
			// mcc_mnc
			if (i == 4) {
				int lastRow = hssfWorkBookSheet.getLastRowNum();
				for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
					mccMncRecord = new MccMnc();
					Row row = hssfWorkBookSheet.getRow(currentRow);
					for (Cell cell : row) {
						if (cell.getColumnIndex() == 0) {
							mccMncRecord.setMcc((int) cell
									.getNumericCellValue());
						}
						if (cell.getColumnIndex() == 1) {
							mccMncRecord.setMnc((int) cell
									.getNumericCellValue());
						}
						if (cell.getColumnIndex() == 2) {
							mccMncRecord.setCountry(cell.getStringCellValue());
						}
						if (cell.getColumnIndex() == 3) {
							mccMncRecord.setOperator(cell.getStringCellValue());
						}
						mccMncList.add(mccMncRecord);
					}
				}
				lookUpDao.addAllMccMnc(mccMncList);
			}
		}
		hssfInputWorkbook.close();
		hssfWorkBook.close();
	}

}
