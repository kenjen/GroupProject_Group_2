package com.project.reader;

import java.io.IOException;

import com.project.dao.LookUpDataDAO;

public interface ReadLookup {

	void setInputFile(String inputFile);
	
	void setSheetNumber(int sheetNumber);

	void read() throws IOException;

	void setLookUpDao(LookUpDataDAO lookupDao);
	

}
