package com.timeseries.model;

import java.util.Date;

public class Record {

	private String instrumentName;
	private Date readingDate;
	private double value;
	private boolean isLastRecord=false;
	
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public Date getReadingDate() {
		return readingDate;
	}
	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Record [instrumentName=" + instrumentName + ", readingDate=" + readingDate + ", value=" + value + "]";
	}
	public boolean isLastRecord() {
		return isLastRecord;
	}
	public void setLastRecord(boolean isLastRecord) {
		this.isLastRecord = isLastRecord;
	}
}
