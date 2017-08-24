package com.timeseries.algo.impl;

import java.util.Date;

import com.timeseries.model.Record;

public class MeanInDateRangeAlgorithm extends MeanCalcAlgorithm{

	
	private Date startDate= new Date(Long.MIN_VALUE);
	private Date endDate= new Date(Long.MAX_VALUE);
    	
	@Override
	public Number process(Record record) {
		Date recordDate= record.getReadingDate();
		if(!(recordDate.compareTo(startDate)==-1 && recordDate.compareTo(endDate)==1)) {
			super.process(record);
		}
		return currentMean;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
