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
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.FileInfo;

@Stateless
public class DirectoryWatcher {
	
	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcher.class);
	
	@EJB
	DirectoryWatcherTransactionInterface dirWatchTransaction;
	
	boolean running = true;
	
	@Asynchronous
	public void poll(String fileSystemPath) throws IOException {
		Path folder = Paths.get("/upload/");
		WatchService watchService = folder.getFileSystem().newWatchService();
		String fileNameS = "";
		//registers folder with types of changes to listen to
		folder.register(watchService, 
				StandardWatchEventKinds.ENTRY_CREATE,
				/*StandardWatchEventKinds.ENTRY_MODIFY,*/
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
						
						/*Path filePathP = fullFilePath.toAbsolutePath();
						String filePathS = filePathP.toFile().toString();
						*/
						
						//rename file due to apache poi registering files as corrupt
						/*File oldFile = new File("/upload/"+fileNameS);
						File newFile = new File("/upload/"+fileNameS.replaceAll(" ", "_").toLowerCase());*/
						//log.info("result of renaming " + oldFile.getAbsolutePath() + "  to  " + newFile.getAbsolutePath() + "  was  " + oldFile.renameTo(newFile));
						//if(oldFile.getName().equals(newFile.getName())){
							Thread.sleep(1000); //wait 1 second to ensure file transfer completed
							log.info("attempting upload of " + "/upload/" + fileNameS);
							FileInfo file = new FileInfo(fileNameS, "/upload/" + fileNameS);
							dirWatchTransaction.addFilePath(file);	
						/*}else{
							if(!(oldFile.renameTo(newFile))){
								//FileInfo file = new FileInfo(fileNameS, "/upload/" + fileNameS);
								log.info("failed to rename file " + oldFile.getAbsolutePath() + "  to  " + newFile.getAbsolutePath());
		
								//in separate class to allow requirement of separate transaction
								//dirWatchTransaction.addFilePath(file);
							}else{
								log.info("successful rename to " + newFile.getName());
							}
						}*/
					}else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
						Path filePath = (Path) event.context();
						Path fileNameP = filePath.getFileName();
						fileNameS = fileNameP.toFile().toString();
						//in separate class to allow requirement of separate transaction
						dirWatchTransaction.removeFileFromDatabase(fileNameS);
						log.info("file deleted: " + filePath);
					}else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						Path filePath = (Path) event.context();
						log.info("file modified: " + filePath);
					}
				}
				
			} catch (IOException e) {
				File oldFile = new File("/upload/"+fileNameS);
				File newFile = new File("/upload/*"+fileNameS.replaceAll(" ", "_").toLowerCase());
				oldFile.renameTo(newFile);
				log.info("Exception Occurred Due to POI bug. Renamed file");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch(IllegalStateException e){
				File oldFile = new File("/upload/"+fileNameS);
				File newFile = new File("/upload/#"+fileNameS.replaceAll(" ", "_").toLowerCase());
				oldFile.renameTo(newFile);
				log.error("IllegalStateException Occurred Due to Component not starting using @Singleton. Renamed to attempt restart");
			}finally {
				if (watchKey != null) {
					boolean valid = watchKey.reset();
					/*log.info("***** key no longer valid, resetting *****");*/
					if (!valid) break; // If the key is no longer valid, the directory is inaccessible so exit the loop.
				}
			}
		}
	}
}
