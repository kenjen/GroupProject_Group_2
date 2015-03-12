package com.project.fileupload;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.LookUpDataDAO;
import com.project.reader.ReadBase;
import com.project.reader.ReadLookup;
import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;
import com.project.service.FileService;

@Stateless
public class DirectoryWatcherTransaction implements DirectoryWatcherTransactionInterface{
	
	@EJB
	FileService fileService;
	
	@EJB
	private BaseDataDAO baseDataDao;
	@EJB
	private ErrorBaseDataDAO errorDao;
	@EJB
	private LookUpDataDAO lookupDao;
	
	ReadLookup lookupDataReader = new ExcelLookupDataRead();
	ReadBase baseDataReader = new ExcelBaseDataRead();

	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcherTransaction.class);
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean addFilePath(String filePath, String fileName) throws IOException{
		if(UploadServlet.getFileExtension(fileName).equals(".xls")){
			try {
				log.info("starting transfer to database with file " + filePath + fileName);
				
				lookupDataReader.setInputFile(filePath + fileName);
				lookupDataReader.setLookUpDao(lookupDao);
				lookupDataReader.read();
				
				log.info("lookup data import finished");
				
				baseDataReader.setSheetNumber(0);
				log.info("input file from directory watcher = " + filePath + fileName);
				baseDataReader.setInputFile(filePath + fileName);
				baseDataReader.setBaseDataDao(baseDataDao);
				baseDataReader.setErrorBaseDataDao(errorDao);
				
				log.info("starting base data transfer with file " + filePath + fileName);
				
				baseDataReader.read();
				
				fileService.addUploadedFilePath(fileName, filePath + fileName, true);
				log.info("Upload completed with " + baseDataReader.getInvalidRowCount() + " invalid rows!!!!!!!!!");
				return true;
			} catch (FileNotFoundException e){
				log.error("file not found exception: " + filePath + fileName);
				return false;
			}
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean removeFileFromDatabase(String fileName) {
		fileService.removeFileFromDatabase(fileName);
		return true;
	}

}
