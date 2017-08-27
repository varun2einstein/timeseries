package com.timeseries.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.model.Record;
import com.timeseries.util.DateUtil;

public class Task implements Runnable {

	private Record record;
	private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
	@Override
	public void run() {

		InstrumentHandler instrumentHandler = RecordHandler.getHandlerMap().get(record.getInstrumentName());
		if (instrumentHandler == null) {
			LOGGER.error("No instrument handler found for Record :" + record.toString());
			return;
		}
		if (DateUtil.isNonBusinessDate(record.getReadingDate())) {
			LOGGER.error("Not a business Day, skipping record :" + record.toString());
			return;
		}
		instrumentHandler.handle(record);
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

}
