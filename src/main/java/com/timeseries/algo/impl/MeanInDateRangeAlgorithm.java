package com.timeseries.algo.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.model.Record;
import com.timeseries.util.DateUtil;

public class MeanInDateRangeAlgorithm extends MeanCalcAlgorithm {

	private static final String START_DATE = "startdate";
	private static final String END_DATE = "enddate";

	private static final Properties dateSinceMeanProperties = new Properties();

	private Date startDate;
	private Date endDate;

	private static final Logger logger = LoggerFactory.getLogger(MeanInDateRangeAlgorithm.class);

	static {
		try {
			dateSinceMeanProperties.load(
					MeanInDateRangeAlgorithm.class.getClassLoader().getResourceAsStream("date-since-mean.properties"));
		} catch (IOException e) {
			logger.error("Error Loading properties for date range for ");
			throw new RuntimeException(e);
		}
	}

	public MeanInDateRangeAlgorithm() {
		try {
			String startDateString = dateSinceMeanProperties.getProperty(START_DATE);
			startDate = startDateString == null || startDateString.isEmpty() ? null
					: DateUtil.getDateForString(startDateString);
			String endDateString = dateSinceMeanProperties.getProperty(END_DATE);
			endDate = endDateString == null || endDateString.isEmpty() ? null
					: DateUtil.getDateForString(endDateString);
		} catch (ParseException e) {
			logger.error("Error Parsing date's :" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public Number process(Record record) {
		boolean processRecord = shouldProcess(record);
		if (processRecord)
			super.process(record);
		return currentMean;
	}

	private boolean shouldProcess(Record record) {
		Date recordDate = record.getReadingDate();
		boolean processRecord = false;
		if (startDate == null && endDate == null) {
			processRecord = true;
		} else if (startDate != null && endDate == null) {
			if (startDate.compareTo(recordDate) < 0) {
				processRecord = true;
			}
		} else if (startDate == null && endDate != null) {
			if (endDate.compareTo(recordDate) >= 0) {
				processRecord = true;
			}
		} else {
			logger.info("Ignoring value " + record.toString() + " as not in date range " + startDate + " -" + endDate);
		}
		return processRecord;
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
