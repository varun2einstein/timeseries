package com.timeseries.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.model.Record;
import com.timeseries.util.DateUtil;

public class RecordHandler {

	private static final Map<String,InstrumentHandler> handlerMap;
	private static final Logger LOGGER= LoggerFactory.getLogger(RecordHandler.class);
	
	static {
		InstrumentHandlerLoader loader=new InstrumentHandlerLoader();
		handlerMap=loader.getInstrumentHandlerMapping();
	}
	
	public RecordHandler() {
	}

	

	public static Map<String, InstrumentHandler> getHandlerMap() {
		return handlerMap;
	}



	public boolean submit(Record record) {
		//apply modifier to the record value
		
		InstrumentHandler instrumentHandler=handlerMap.get(record.getInstrumentName());
		if(instrumentHandler==null) {
			LOGGER.error("No instrument handler found for Record :"+ record.toString());
			return false;
		}
		if(DateUtil.isNonBusinessDate(record.getReadingDate())) {
			LOGGER.error("Not a business Day, skipping record :"+record.toString());
			return false;
		}
		instrumentHandler.handle(record);
		return true;
	}
	
	
}
