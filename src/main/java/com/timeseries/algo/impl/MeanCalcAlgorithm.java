package com.timeseries.algo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.algo.Algorithm;
import com.timeseries.model.Record;

public class MeanCalcAlgorithm implements Algorithm {

	public static final Logger logger= LoggerFactory.getLogger(MeanCalcAlgorithm.class);
	protected Double currentMean=0.0;
	protected int numCount;
	
	public int getNumCount() {
		return numCount;
	}

	public void setNumCount(int numCount) {
		this.numCount = numCount;
	}

	public Number process(Record record) {
		numCount++;
		currentMean=((currentMean*(numCount-1))+record.getValue())/(numCount);
		return currentMean;
	}
	
	public Double getCurrentMean() {
		return currentMean;
	}

}
