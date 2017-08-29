package com.timeseries.util;

import java.text.ParseException;
import java.util.Date;

import com.timeseries.model.Record;

public class RecordMapper {

	public static Record mapRecord(String recordString) throws ParseException {
		
		String [] tokens=recordString.split(",");
		if(tokens.length!=3) {
			throw new RuntimeException("Invalid Record Found");
		}
		Record record= new Record();
		record.setInstrumentName(tokens[0]);
		Date date= DateUtil.getDateForString(tokens[1]);
		record.setReadingDate(date);
		record.setValue(Double.parseDouble(tokens[2]));
		return record;
	}
}
