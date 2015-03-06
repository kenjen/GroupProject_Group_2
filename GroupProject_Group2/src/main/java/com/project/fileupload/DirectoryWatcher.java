package com.project.fileupload;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.PersistenceUtil;

import com.project.entities.FileInfo;

@Local
@Startup
@Singleton
public class DirectoryWatcher implements DirectoryWatcherInterface{
	
	//private instance to ensure only one Directory watcher in existence
	private static DirectoryWatcher watcher;
	
	private static WatchService watchService;
	
	private static Thread t;
	
	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcher.class);

	@PostConstruct
	public void initialize(){
		try {
			watcher = createDirectoryWatcher();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DirectoryWatcher(){
		
	}
	
	public static DirectoryWatcher createDirectoryWatcher() throws IOException{
		// Folder we are going to watch
		Path folder = Paths.get("/home/group2/");
		
		// Create a new Watch Service
		watchService = FileSystems.getDefault().newWatchService();
		log.info("DirectoryWatcher created");
		// Register events
		//folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		folder.register(watchService, 
				StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);
		
		t = new Thread(new Runnable() {
			public void run() {
				boolean running = true;
				while(running){
					try {
						WatchKey watchKey = watchService.take();
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
								PersistenceUtil.persist(file);
							}
						}
					} catch (InterruptedException e) {
						running = false;
						e.printStackTrace();
					}
					
				}
			}
		});
		t.start();
		return watcher;
	}
	
	public static DirectoryWatcher getDirectoryWatcher() throws IOException{
		if(watcher==null){
			watcher = createDirectoryWatcher();
		}
		return watcher;
	}
	
	/*
	 * return true if service was started
	 * return false if already started
	 */
	
	public static boolean startWatching() throws IOException{
		if(watcher==null){
			getDirectoryWatcher();
			return true;
		}
		return false;
	}

	public static void stopWatching() throws IOException{
		// Closes a watch service
		watchService.close();
	}
}
