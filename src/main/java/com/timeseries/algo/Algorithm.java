package com.timeseries.algo;

import com.timeseries.model.Record;

public interface Algorithm {
	public Number process(Record record);
}
