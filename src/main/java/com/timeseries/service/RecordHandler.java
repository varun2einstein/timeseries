package com.timeseries.service;

import java.util.HashMap;
import java.util.Map;

import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.model.Record;

public class RecordHandler {

	Map<String,InstrumentHandler> map= new HashMap<String, InstrumentHandler>();
	
	public void submit(Record record) {
		//apply modifier to the record value
		
		InstrumentHandler instrumentHandler=map.get(record.getInstrumentName());
		instrumentHandler.handle(record);
	}
}
