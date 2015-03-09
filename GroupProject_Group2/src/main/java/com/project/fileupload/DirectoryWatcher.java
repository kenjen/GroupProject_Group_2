package com.project.fileupload;

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
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.dao.FileDAO;
import com.project.entities.FileInfo;
import com.project.service.FileService;

@Stateless
public class DirectoryWatcher {
	
	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcher.class);
	
	@EJB
	DirectoryWatcherTransactionInterface dirWatchTransaction;
	
	boolean running = true;
	
	@Asynchronous
	public void poll(String fileSystemPath) throws IOException {
		/*Path folder = Paths.get(fileSystemPath);*/
		Path folder = Paths.get("/upload/");
		WatchService watchService = folder.getFileSystem().newWatchService();
		folder.register(watchService, 
				StandardWatchEventKinds.ENTRY_CREATE,
				/*StandardWatchEventKinds.ENTRY_MODIFY,*/
				StandardWatchEventKinds.ENTRY_DELETE);
		while(running) {
			WatchKey watchKey = null;
			log.info("polling on " + folder.toAbsolutePath());
			try {
				watchKey = watchService.take();
				
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent event : events) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						Path pathNewFile = (Path) event.context();
						Path fileNameP = pathNewFile.getFileName();
						String fileNameS = fileNameP.toFile().toString();
						Path filePathP = pathNewFile.toAbsolutePath();
						String filePathS = filePathP.toFile().toString();
						FileInfo file = new FileInfo(fileNameS, filePathS);
						log.info("file created: filename = " + fileNameS + "    filepath = " + filePathS);
						
						dirWatchTransaction.addFilePath(file);
						
						log.info("file persisted: filename = " + file.getFilename() + "    filepath = " + file.getFilepath());
					}else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
						Path filePath = (Path) event.context();
						log.info("file deleted: " + filePath);
					}else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						Path filePath = (Path) event.context();
						log.info("file modified: " + filePath);
					}
				}
				//watchKey.reset();
				
			} catch (Exception e) {
				e.printStackTrace();
				return;
			} finally {
				if (watchKey != null) {
					boolean valid = watchKey.reset();
					log.info("***** key no longer valid, resetting *****");
					if (!valid) break; // If the key is no longer valid, the directory is inaccessible so exit the loop.
				}
			}
		}
	}
}
