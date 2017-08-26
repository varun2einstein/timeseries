package com.timeseries.algo.impl;

import com.timeseries.algo.Algorithm;
import com.timeseries.model.Record;

public class MeanCalcAlgorithm implements Algorithm {

	protected Double currentMean=0.0;
	protected int numCount;
	
	public Number process(Record record) {
		numCount++;
		currentMean=((currentMean*(numCount-1))+record.getValue())/(numCount);
		return currentMean;
	}
	
	public Double getCurrentMean() {
		return currentMean;
	}

}
