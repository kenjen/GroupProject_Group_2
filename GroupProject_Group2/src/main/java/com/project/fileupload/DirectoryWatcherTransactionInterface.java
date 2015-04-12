package com.project.fileupload;

import java.io.IOException;

public interface DirectoryWatcherTransactionInterface {

	boolean addFilePath(String filePath, String fileName) throws IOException;

	boolean removeFileFromDatabase(String fileNameS);
}
