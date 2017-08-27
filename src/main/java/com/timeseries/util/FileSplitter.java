package com.timeseries.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSplitter {

	private static final Logger logger = LoggerFactory.getLogger(FileSplitter.class);
	private int partNumber = 1;
	
	private static final int MAX_ROWS=100;

	public void splitFile(String filePath) {

		URI uri;
		try {
			uri = new URI(filePath);
		} catch (URISyntaxException e1) {
			logger.error("Invalid filepath specified " + e1.getMessage());
			throw new RuntimeException(e1);
		}

		Path path = Paths.get(uri);

		File file = path.toFile();

		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("Invalid filepath specified " + e.getMessage());
			throw new RuntimeException(e);
		}

		BufferedReader bufr = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String fName = file.getName();
		try {
			
			while(bufr.re) {
				
			}
			
			int linesWritten=0;
			String line;
			String filePartName = String.format("%s.%03d", fName, partNumber++);
			File newFile = new File(file.getParent(), filePartName);
			while ((line = bufr.readLine()) != null && linesWritten<MAX_ROWS) {
				try (FileWriter out = new FileWriter(newFile)) {
					out.write(line);
					linesWritten++;
				}
			}
		} catch (IOException e) {
			logger.error("Could not read file " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (bufr != null) {
				try {
					bufr.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}

	}
}
