package com.project.reader.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.entities.BaseData;
import com.project.entities.ErrorBaseData;
import com.project.entities.EventCause;
import com.project.reader.ReadBase;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;

@Stateless
@Local
public class ExcelBaseDataRead implements ReadBase {

	private String inputFile;
	@Inject
	private ExcellValidator validator;

	private int sheetNumber;
	private int invalidRowCount;

	private BaseDataDAO baseDataDao;
	private ErrorBaseDataDAO errorBaseDataDao;

	public ExcelBaseDataRead() {
	}

	@Override
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	@Override
	public void read() throws IOException {
		invalidRowCount = 0;
		FileInputStream hssfInputWorkbook = new FileInputStream(new File(
				inputFile));

		List<BaseData> baseDatList = new ArrayList<BaseData>();
		List<ErrorBaseData> errorBaseDatList = new ArrayList<ErrorBaseData>();
		BaseData baseDataRecord = null;

		HSSFWorkbook hssfWorkBook;
		hssfWorkBook = new HSSFWorkbook(hssfInputWorkbook);
		HSSFSheet hssfWorkBookSheet = hssfWorkBook.getSheetAt(sheetNumber);

		int lastRow = hssfWorkBookSheet.getLastRowNum();

		DataFormatter df = new DataFormatter();
		for (int currentRow = 1; currentRow <= lastRow; ++currentRow) {
			baseDataRecord = new BaseData();
			Row row = hssfWorkBookSheet.getRow(currentRow);
			boolean rowValid = true;
			for (Cell cell : row) {
				if (cell.getColumnIndex() == 0) {
					try {
						baseDataRecord.setDate(cell.getDateCellValue());
					}catch(Exception e){
						baseDataRecord.setDate(new Date(1l));
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 1) {
					try {
						baseDataRecord.setEventId((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setEventId(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 2) {
					try {
						baseDataRecord.setFailureClass((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setFailureClass(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 3) {
					try {
						baseDataRecord.setTac((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setTac(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 4) {
					try {
						baseDataRecord.setMcc((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setMcc(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 5) {
					try {
						baseDataRecord.setMnc((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setMnc(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 6) {
					try {
						baseDataRecord.setCellId((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setCellId(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 7) {
					try {
						baseDataRecord.setDuration((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setDuration(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 8) {
					try {
						baseDataRecord.setCauseCode((int) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setCauseCode(-1);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 9) {
					try {
						baseDataRecord.setNeVersion(cell.getStringCellValue());
					} catch (Exception e) {
						baseDataRecord.setNeVersion("INVALID");
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 10) {
					try {
						baseDataRecord.setImsi((long) cell.getNumericCellValue());
					} catch (Exception e) {
						baseDataRecord.setImsi(-1l);
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 11) {
					try {
						String cellVal = df.formatCellValue(cell);
						baseDataRecord.setHier3Id(cellVal);
					} catch (Exception e) {
						baseDataRecord.setHier3Id("INVALID");
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 12) {
					try {
						String cellVal = df.formatCellValue(cell);
						baseDataRecord.setHier32Id(cellVal);
					} catch (Exception e) {
						baseDataRecord.setHier32Id("INVALID");
						rowValid = false;
					}
				}
				if (cell.getColumnIndex() == 13) {
					try {
						String cellVal = df.formatCellValue(cell);
						baseDataRecord.setHier321Id(cellVal);
					} catch (Exception e) {
						baseDataRecord.setHier321Id("INVALID");
						rowValid = false;
					}
				}
			}
			/*
			 * PASS RELATIONAL COLUMN DATA TO LOOKUP TABLES TO OBTAIN AN OBJECT
			 * REPRESENTING THE RELATIONSHIP BASED ON THE COLUMN(S) PASSED
			 */
			setAllLinks(baseDataRecord);
			if (rowValid && validator.isValid(baseDataRecord)) {
				baseDatList.add(baseDataRecord);
			} else {
				invalidRowCount++;
				errorBaseDatList.add(new ErrorBaseData(baseDataRecord));
			}
		}
		hssfInputWorkbook.close();
		hssfWorkBook.close();
		baseDataDao.addAllBaseData(baseDatList);
		errorBaseDataDao.addAllErrorBaseData(errorBaseDatList);
	}

	/*
	 * WE RETURN AN OBJECT REPRESENTING THE LOOKUP TABLE FROM THE VALUES PASSED
	 */
	private void setAllLinks(BaseData bd) {
		if (bd.getCauseCode() != null && bd.getEventId() != null) {
			EventCause ec = ExcelLookupDataRead.getEventCause(
					bd.getCauseCode(), bd.getEventId());
			bd.setEventCauseFK(ec);
		}

		if (bd.getFailureClass() != null) {
			FailureClass fc = ExcelLookupDataRead.getFailureClass(bd
					.getFailureClass());
			bd.setFaliureClassFK(fc);
		}

		if (bd.getTac() != null) {
			UE ue = ExcelLookupDataRead.getUe(bd.getTac());
			bd.setUeFK(ue);
		}

		if (bd.getMcc() != null && bd.getMnc() != null) {
			MccMnc mc = ExcelLookupDataRead.getMccMnc(bd.getMcc(), bd.getMnc());
			bd.setMccMncFK(mc);
		}
	}

	@Override
	public void setSheetNumber(int sheetNumber) {
		this.sheetNumber = sheetNumber;
	}

	public void setBaseDataDao(BaseDataDAO dao) {
		this.baseDataDao = dao;
	}

	public void setErrorBaseDataDao(ErrorBaseDataDAO dao) {
		this.errorBaseDataDao = dao;
	}

	public int getInvalidRowCount() {
		return invalidRowCount;
	}

}
