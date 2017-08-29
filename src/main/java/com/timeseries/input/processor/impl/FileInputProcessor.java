package com.timeseries.input.processor.impl;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.input.processor.TimeSeriesInputProcessor;
import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.instrument.handler.impl.InstrumentHandlerImpl;
import com.timeseries.util.FileSplitter;

public class FileInputProcessor implements TimeSeriesInputProcessor {

    private String filePath;
	private FileHandler fileHandler;

	public FileHandler getFileHandler() {
		return fileHandler;
	}


	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	private static final Logger logger= LoggerFactory.getLogger(FileInputProcessor.class);
	
	@Override
	public void process() {
		try {
			// split file and place all the parts in the same directory as the file
			FileSplitter.splitFile(filePath);

			File file = new File(filePath);

			// get parent directory of the file

			File parentDirectory = file.getParentFile();

			if (parentDirectory.isDirectory()) {

				File[] allFiles = parentDirectory.listFiles();
				String fileName = file.getName();

				for (File f : allFiles) {
					if (f.getName().matches(fileName + ".+")) {
						fileHandler.submit(f);
					}
				}
				
				boolean isShutDown=fileHandler.shutDownFileProcessor();
				
				//indicate ro record handler that all records are submitted for processing and no more left
				if(isShutDown) {
					fileHandler.submitPoisonPill();
				}
				
				Map<String, InstrumentHandler> results=fileHandler.getResults();
				
				for(Entry<String, InstrumentHandler> entry:results.entrySet()) {
					logger.info(entry.getKey()+" : "+((InstrumentHandlerImpl)entry.getValue()).getValue());
				}
			}
		} catch (Exception e) {
			if(fileHandler!=null) {
				fileHandler.stopTheWorld();
			}
		}
	}
	

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
