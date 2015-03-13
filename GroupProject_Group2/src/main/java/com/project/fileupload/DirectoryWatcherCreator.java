package com.project.fileupload;

import java.io.File;
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
    private DirectoryWatcherInterface directoryWatcher;
	
	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcherCreator.class);
	
	private static boolean started = false;
	private static String fileSystemPath;
	private static boolean directoryAvailable = true;

	@PostConstruct
    public void initialise() throws IOException {
		started = true;
		//fileSystemPath = "";
		File directory;
		/*if((System.getProperty("os.name").substring(0, 7).toLowerCase()).equals("windows")){
			fileSystemPath = "c:/upload/";
			log.info("windows detected");
			directory = new File(fileSystemPath);
		}else{
			fileSystemPath = "/upload/";
			log.info("windows not detected, assumming linux");
			directory = new File(fileSystemPath);
		}*/
		try{
			if((System.getProperty("os.name").substring(0, 7).toLowerCase()).equals("windows")){
				fileSystemPath = "c:/upload/";
				log.info("windows detected");
				directory = new File(fileSystemPath);
			}else{
				fileSystemPath = "/upload/";
				log.info("windows not detected, assumming linux");
				directory = new File(fileSystemPath);
			}
		}catch(Exception e){
			fileSystemPath = "/upload/";
			log.info("windows not detected, assumming linux");
			directory = new File(fileSystemPath);
		}
		
		if(directory.exists()){
	        directoryWatcher.poll(fileSystemPath);
	        log.info("directory watcher initialised");
		}else{
			log.info("directory does not exist");
			directoryAvailable = false;
		}
	}
	
	public static boolean isStarted(){
		return started;
	}
	
	public static String getFileSystemPath(){
		return fileSystemPath;
	}
	
	public static boolean isDirectoryAvailable(){
		return directoryAvailable;
	}

}
