package com.project.fileupload;

import java.io.IOException;

import javax.ejb.Asynchronous;

public interface DirectoryWatcherInterface {

	@Asynchronous
	void poll(String fileSystemPath) throws IOException;
}
