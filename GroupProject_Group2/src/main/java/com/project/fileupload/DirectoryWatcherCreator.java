package com.project.fileupload;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class DirectoryWatcherCreator {
	
	@EJB
    private DirectoryWatcher directoryWatcher;
	
	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcherCreator.class);

	@PostConstruct
    public void initialise() throws IOException {
        String fileSystemPath = "/upload/";
        directoryWatcher.poll(fileSystemPath);
        log.info("directory watcher initialised");
    }

}
