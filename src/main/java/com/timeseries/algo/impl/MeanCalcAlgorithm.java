package com.timeseries.algo.impl;

import com.timeseries.algo.Algorithm;
import com.timeseries.model.Record;

public class MeanCalcAlgorithm implements Algorithm {

	protected Double currentMean;
	protected int numCount;
	
	public Number process(Record record) {
		numCount++;
		currentMean=(currentMean*numCount)/(numCount);
		return currentMean;
	}
	
	public Double getCurrentMean() {
		return currentMean;
	}

}
