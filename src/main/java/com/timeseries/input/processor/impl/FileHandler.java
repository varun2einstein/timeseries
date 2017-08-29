package com.timeseries.input.processor.impl;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.model.Record;
import com.timeseries.service.RecordHandler;

public class FileHandler {
	private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
	private RecordHandler recordHandler;
	private ExecutorService executorService = Executors.newCachedThreadPool();

	public void submit(File filePart) {
		FileTask fileTask = new FileTask();
		fileTask.recordHandler = this.recordHandler;
		fileTask.filePart = filePart;
		if(recordHandler.isProcessingDone()||recordHandler.isStopProcessing()) {
			executorService.shutdown();
		}
		executorService.submit(fileTask);
	}

	public RecordHandler getRecordHandler() {
		return recordHandler;
	}

	public void setRecordHandler(RecordHandler recordHandler) {
		this.recordHandler = recordHandler;
	}

	public boolean shutDownFileProcessor() {
		executorService.shutdown();
		try {
			executorService.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			logger.error("Interrupted while waiting for termination of file processing "+ e.getMessage());
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
		return true;
	}

	public void submitPoisonPill() {
		recordHandler.submit(createPoisonPill());
	}

	private Record createPoisonPill() {
		Record record = new Record();
		record.setLastRecord(true);
		return record;
	}
	
	public Map<String, InstrumentHandler> getResults(){
		Map<String, InstrumentHandler> resultMap=null;
		while(!recordHandler.isProcessingDone()) {
			resultMap=RecordHandler.getHandlerMap();
		}
		return resultMap;
	}
	
	public void stopTheWorld() {
		recordHandler.setStopProcessing(true);
		shutDownFileProcessor();
	}
}