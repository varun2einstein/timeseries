package com.timeseries.instrument.handler;

import com.timeseries.model.Record;

public interface InstrumentHandler {

	public void handle(Record record);
}
