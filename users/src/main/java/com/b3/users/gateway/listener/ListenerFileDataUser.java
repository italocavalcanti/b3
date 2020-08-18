package com.b3.users.gateway.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b3.users.gateway.listener.readfile.ReadFileUsers;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor
public class ListenerFileDataUser {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	final static String DIR = "app/files/";
	final static String EXTENSION = ".csv";
	File folder = new File(DIR);
	
	private ReadFileUsers readFileUsers;
	
	@Autowired
	ListenerFileDataUser (ReadFileUsers readFileUsers){
		this.readFileUsers = readFileUsers;
	}

	public void read() {
		final Runnable read = new Runnable() {
			public void run() {
				try {

					File[] listOfFiles = folder.listFiles();

					for (File file : listOfFiles) {
						if (file.isFile() && file.getName().contains(EXTENSION)) {
							log.info("------ Processando arquivo: {}", file.getName());
							
							FileReader fileReader = new FileReader(file.getAbsoluteFile());
							readFileUsers.process(fileReader);
							
							log.info("------  arquivo processado: {}", file.getName());
							fileReader.close();
							file.delete();
						}
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
					log.error("Arquivo Não Encontrado");
				} catch (Exception e) {
					log.error("Error {}", e.getCause());
				}
			}
		};

		scheduler.scheduleAtFixedRate(read, 0, 30, TimeUnit.SECONDS);
	}
}
