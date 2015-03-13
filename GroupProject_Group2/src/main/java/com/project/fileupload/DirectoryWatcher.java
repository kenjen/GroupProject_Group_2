package com.project.fileupload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Remote
public class DirectoryWatcher implements DirectoryWatcherInterface{
	
	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcher.class);
	
	@EJB
	DirectoryWatcherTransactionInterface dirWatchTransaction;
	
	boolean running = true;
	
	private static String systemFilePath = "";
	
	@Override
	@Asynchronous
	public void poll(String fileSystemPath) throws IOException {
		systemFilePath = fileSystemPath;
		Path folder = Paths.get(systemFilePath);
		WatchService watchService = folder.getFileSystem().newWatchService();
		String fileNameS = "";
		//registers folder with changes to listen for
		folder.register(watchService, 
				StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE);
		log.info("polling on " + folder.toAbsolutePath());
		while(running) {
			WatchKey watchKey = null;
			try {
				//loop waits here until a standard watch event occurs
				watchKey = watchService.take();
				
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent event : events) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						Path fullFilePath = (Path) event.context();
						Path fileNameP = fullFilePath.getFileName();
						fileNameS = fileNameP.toFile().toString();
						Thread.sleep(1000); //wait 1 second to ensure file transfer completed
						log.info("attempting upload of " + systemFilePath + fileNameS);
						dirWatchTransaction.addFilePath(systemFilePath, fileNameS);	//in separate class to allow requirement of separate transaction
					}else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
						Path filePath = (Path) event.context();
						Path fileNameP = filePath.getFileName();
						fileNameS = fileNameP.toFile().toString();
						dirWatchTransaction.removeFileFromDatabase(fileNameS);	//in separate class to allow requirement of separate transaction
						log.info("file deleted: " + filePath);
					}
				}
			} catch (IOException e) {
				File oldFile = new File(systemFilePath+fileNameS);
				File newFile = new File(systemFilePath+fileNameS.replaceAll(" ", "_").toLowerCase());
				oldFile.renameTo(newFile);
				log.info("Exception Occurred Due to POI bug. Renamed file");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (watchKey != null) {
					boolean valid = watchKey.reset();
					if (!valid) break; // If the key is no longer valid, the directory is inaccessible so exit the loop.
				}
			}
		}
	}
}
