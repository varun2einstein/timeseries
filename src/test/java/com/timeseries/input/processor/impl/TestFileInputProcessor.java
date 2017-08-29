package com.timeseries.input.processor.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timeseries.CommonTest;
import com.timeseries.service.RecordHandler;
import com.timeseries.util.FileSplitter;

public class TestFileInputProcessor {
    
	static FileInputProcessor fileInputProcessor;
	static FileHandler fileHandler;
	static RecordHandler recordHandler;
	static URL url;
	
	@BeforeClass
	public static void init() throws SQLException, IOException {
		try {
			CommonTest.init();
			fileInputProcessor= new FileInputProcessor();
			url=TestFileInputProcessor.class.getClassLoader().getResource("example_input.txt");
			fileInputProcessor.setFilePath(url.getPath());
			fileHandler= new FileHandler();
			recordHandler=new RecordHandler();
			fileHandler.setRecordHandler(recordHandler);
			fileInputProcessor.setFileHandler(fileHandler);
		} catch (Exception e) {
			destroy();
		}
	}
	
	@Test
	public void testProcess() {
		try {
			fileInputProcessor.process();
		} catch (Exception e) {
			destroy();
		}
	}
	
	@AfterClass
	public static void destroy() {
		try {
			CommonTest.destroy();

			File file= new File(url.getPath());
			
			File directory=file.getParentFile();
			
			FileSplitter.deleteAllPartitions(directory,file.getName());
			

			fileInputProcessor=null;
			fileHandler=null;
			recordHandler=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
