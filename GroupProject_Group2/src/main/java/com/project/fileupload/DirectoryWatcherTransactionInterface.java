package com.project.fileupload;

import java.io.IOException;

import com.project.entities.FileInfo;

public interface DirectoryWatcherTransactionInterface {

	void addFilePath(FileInfo file) throws IOException;

	void removeFileFromDatabase(String fileNameS);

}
