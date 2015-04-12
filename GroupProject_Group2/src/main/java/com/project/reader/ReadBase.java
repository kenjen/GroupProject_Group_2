package com.project.reader;

import java.io.IOException;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;

public interface ReadBase {

	void setInputFile(String inputFile);
	
	void setSheetNumber(int sheetNumber);

	void read() throws IOException;

	void setBaseDataDao(BaseDataDAO dao);

	void setErrorBaseDataDao(ErrorBaseDataDAO errorDao);

	int getInvalidRowCount();

}
