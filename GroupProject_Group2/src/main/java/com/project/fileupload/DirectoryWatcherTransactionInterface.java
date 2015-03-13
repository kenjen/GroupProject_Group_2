package com.project.fileupload;

import java.io.IOException;

public interface DirectoryWatcherTransactionInterface {

	void addFilePath(String filePath, String fileName) throws IOException;

	void removeFileFromDatabase(String fileNameS);
}
