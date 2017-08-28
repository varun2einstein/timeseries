package com.timeseries.util;

import java.io.File;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.timeseries.util.FileSplitter;

public class TestFileSplitter {

	private FileSplitter fileSplitter=new FileSplitter();
	public static final String FILE_NAME="example_input.txt";
	public static URL url=null;
	
	@Before
	public void init() {
		url=this.getClass().getClassLoader().getResource(FILE_NAME);
	}
	
	@Test
	public void testFileSplitter() {
		URL url=this.getClass().getClassLoader().getResource(FILE_NAME);
		fileSplitter.splitFile(url.getPath());
		
	}
	
	@AfterClass
	public static void cleanUp() {
		File file= new File(url.getPath());
		
		File directory=file.getParentFile();
		
		if(directory.isDirectory()) {
			File[] files=directory.listFiles();
			
			for(File f:files) {
				if(f.getName().matches(FILE_NAME+".+")) {
					f.delete();
				}
			}
		}
		
	}
}