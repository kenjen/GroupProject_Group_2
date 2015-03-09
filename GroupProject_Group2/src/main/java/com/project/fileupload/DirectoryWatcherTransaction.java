package com.project.fileupload;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.FileDAO;
import com.project.dao.LookUpDataDAO;
import com.project.entities.FileInfo;
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

	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcherCreator.class);
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void addFilePath(FileInfo file){
		if(UploadServlet.getFileExtension(file.getFilename()).equals(".xls")){
			try {
				lookupDataReader.setInputFile("/upload" + file.getFilepath());
				lookupDataReader.setLookUpDao(lookupDao);
				lookupDataReader.read();
				
				baseDataReader.setSheetNumber(0);
				log.info("input file from directory watcher = " + "/upload" + file.getFilepath());
				baseDataReader.setInputFile("/upload" + file.getFilepath().trim());
				baseDataReader.setBaseDataDao(baseDataDao);
				baseDataReader.setErrorBaseDataDao(errorDao);
				baseDataReader.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileService.addUploadedFilePath(file.getFilename(), "/upload" + file.getFilepath(), true);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void removeFileFromDatabase(String fileName) {
		fileService.removeFileFromDatabase(fileName);
	}

}
