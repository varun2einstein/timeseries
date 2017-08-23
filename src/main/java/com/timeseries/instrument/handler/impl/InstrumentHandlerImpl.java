package com.timeseries.instrument.handler.impl;

import com.timeseries.algo.Algorithm;
import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.model.Record;

public class InstrumentHandlerImpl implements InstrumentHandler {

	private Algorithm algorithm;
	private Number value;
	
	public void handle(Record record) {
		setValue(algorithm.process(record));
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}


	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

}
