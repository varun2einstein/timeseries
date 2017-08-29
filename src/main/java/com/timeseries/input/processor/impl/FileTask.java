package com.timeseries.input.processor.impl;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.model.Record;
import com.timeseries.service.RecordHandler;
import com.timeseries.util.RecordMapper;

public class FileTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(FileTask.class);

	File filePart;
	RecordHandler recordHandler;
    
	@Override
	public void run() {
		// read the file part line by line
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(filePart);
		} catch (FileNotFoundException e) {
			logger.error("File part not found :"+filePart + " All records will be skipped for this file part");
			closeSilently(fis);
			throw new RuntimeException(e);
		}
		
		BufferedReader fileReader=null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("File Encoding not supported");
			Closeable[] resources= {fis,fileReader};
			closeSilently(resources);
		    throw new RuntimeException(e);
		}
        
		
		try {
			String recordLine=null;
			while((recordLine=fileReader.readLine())!=null) {
				Record record= RecordMapper.mapRecord(recordLine);
				recordHandler.submit(record);
			}
		} catch (IOException e) {
			logger.error("Unable to read file part "+filePart.getName());
		    throw new RuntimeException(e);
		} catch (ParseException e) {
			logger.error("Unable to read file part "+filePart.getName() + " errorneous record for date");
		    throw new RuntimeException(e);
		}finally {
			Closeable[] resources= {fis,fileReader};
			closeSilently(resources);
		}
		
	}
	
	private void closeSilently(Closeable closable) {
		try {
			if(closable!=null)
			   closable.close();
		} catch (IOException e) {
			logger.debug("Closing resource silenty");
		}
	}
	
	private void closeSilently(Closeable...closable) {
			for(Closeable cl:closable) {
				closeSilently(cl);
			}
	}
}
