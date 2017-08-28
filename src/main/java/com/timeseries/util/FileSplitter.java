package com.timeseries.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSplitter {

	private static final Logger logger = LoggerFactory.getLogger(FileSplitter.class);
	private static final int MAX_ROWS = 100;

	public void splitFile(String filePath) {
		File file = new File(filePath);
		createParts(file);
	}

	private void createParts(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("Invalid filepath specified " + e.getMessage());
			throw new RuntimeException(e);
		}

		BufferedReader bufr = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String fName = file.getName();

		// initialize part number to 0;
		int partNumber = 0;
		String currentline = null;
		int currentLineNumber = 0;
		File partFile = null;
		String filePartName = null;
		FileWriter out=null;
		// in a loop read lines
		try {
			while ((currentline = bufr.readLine()) != null) {
				// if currentLineNumber is 0 then create a new partfile.
				if (currentLineNumber == MAX_ROWS) {
					currentLineNumber = 0;
					out.close();
				}
				if (currentLineNumber == 0) {
					filePartName = String.format("%s.%03d", fName, partNumber++);
					partFile = new File(file.getParent(), filePartName);
					out = new FileWriter(partFile);
				}
				// write the line to partfile

				out.write(currentline+System.lineSeparator());
				currentLineNumber++;
			}
		} catch (IOException e) {
			logger.error("Could not read file " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (bufr != null) {
				try {
					bufr.close();
				} catch (IOException e) {
					logger.error("Error closing buffered stream, ignoring error");
				}
			}
		}
	}
}
