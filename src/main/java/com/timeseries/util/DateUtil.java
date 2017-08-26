package com.timeseries.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    
	public static final String datePattern="dd-MMM-yyyy";
	
	
	public static Date getDateForString(String dateString) throws ParseException {
		SimpleDateFormat dateFormat= new SimpleDateFormat(datePattern);
		Date date=dateFormat.parse(dateString);
		return date;
	}
	
	public static boolean isNonBusinessDate(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		int dayofWeek=c.get(Calendar.DAY_OF_WEEK);
		if(dayofWeek==Calendar.SATURDAY||dayofWeek==Calendar.SUNDAY) {
			return true;
		}
		return false;
	}
}
