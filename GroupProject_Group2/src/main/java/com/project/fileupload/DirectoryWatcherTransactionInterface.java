package com.project.fileupload;

import com.project.entities.FileInfo;

public interface DirectoryWatcherTransactionInterface {

	void addFilePath(FileInfo file);

	void removeFileFromDatabase(String fileNameS);

}
