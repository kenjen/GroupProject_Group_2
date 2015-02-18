package com.project.reader.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.ErrorData;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.JPABaseDataDAO;
import com.project.entities.BaseData;
import com.project.entities.ErrorBaseData;
import com.project.reader.Read;

public class ExcelBaseDataRead implements Read {

	private String inputFile;
	private final ExcellValidator validator = new ExcellValidator();

	private int sheetNumber;

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
				try{
					if (cell.getColumnIndex() == 0) {
						baseDataRecord.setDate(cell.getDateCellValue());
					}
					if (cell.getColumnIndex() == 1) {
						baseDataRecord.setEventId((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 2) {
						baseDataRecord.setFailureClass((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 3) {
						baseDataRecord.setTac((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 4) {
						baseDataRecord.setMcc((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 5) {
						baseDataRecord.setMnc((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 6) {
						baseDataRecord.setCellId((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 7) {
						baseDataRecord.setDuration((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 8) {
						baseDataRecord.setCauseCode((int) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 9) {
						baseDataRecord.setNeVersion(cell.getStringCellValue());
					}
					if (cell.getColumnIndex() == 10) {
						baseDataRecord.setImsi((long) cell.getNumericCellValue());
					}
					if (cell.getColumnIndex() == 11) {
						String cellVal = df.formatCellValue(cell);
						baseDataRecord.setHier3Id(cellVal);
					}
					if (cell.getColumnIndex() == 12) {
						String cellVal = df.formatCellValue(cell);
						baseDataRecord.setHier32Id(cellVal);
					}
					if (cell.getColumnIndex() == 13) {
						String cellVal = df.formatCellValue(cell);
						baseDataRecord.setHier321Id(cellVal);
					}
					
				}catch(Exception e){
					//catches all errors while parsing the data and marks the row as invalid
					rowValid = false;
					break;
				}
			}
			if(rowValid && validator.isValid(baseDataRecord)){
				baseDatList.add(baseDataRecord);
			}else{
				errorBaseDatList.add(new ErrorBaseData(row));
			}
		}
		hssfInputWorkbook.close();
		hssfWorkBook.close();
		baseDataDao.addAllBaseData(baseDatList);
		errorBaseDataDao.addAllErrorBaseData(errorBaseDatList);
	}

	@Override
	public void setSheetNumber(int sheetNumber) {
		this.sheetNumber = sheetNumber;
	}
	
	public void setBaseDataDao(BaseDataDAO dao){
		this.baseDataDao = dao;
	}
	
	public void setErrorBaseDataDao(ErrorBaseDataDAO dao){
		this.errorBaseDataDao = dao;
	}

}
