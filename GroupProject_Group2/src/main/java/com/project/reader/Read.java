package com.project.reader;

import java.io.IOException;

public interface Read {

	void setInputFile(String inputFile);
	
	void setSheetNumber(int sheetNumber);

	void read() throws IOException;

}
