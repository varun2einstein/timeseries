package com.timeseries.data;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.CommonTest;
import com.timeseries.model.Record;
import com.timeseries.util.DateUtil;

public class RecordTestData {

	public static Record[] recordsInstrument1;
	public static Record[] recordsInstrument2;
	public static Record[] recordsInstrument3;

	private static final Date startDate;
	private static final Date endDate;

	public static void initData() {
		recordsInstrument1 = new Record[10];

		recordsInstrument2 = new Record[10];

		recordsInstrument3 = new Record[10];
	}

	private static final Logger logger = LoggerFactory.getLogger(RecordTestData.class);

	static {
		try {
			startDate = DateUtil.getDateForString("01-Jan-2012");
			endDate = DateUtil.getDateForString("11-Jan-2012");
		} catch (ParseException e) {
			logger.error(e.getMessage() + " \n" + e.getCause().getMessage());
			throw new RuntimeException(e);
		}
	}

	public static void createTestData() {
		createRecordsForInstrument1();
		createRecordsForInstrument2();
		createRecordsForInstrument3();
	}

	private static Record createRecord(String instrumentName, Date readingDate, Double value) {
		Record record = new Record();
		record.setInstrumentName(instrumentName);
		record.setReadingDate(readingDate);
		record.setValue(value);
		return record;
	}

	private static void createRecordsForInstrument1() {
		for (int i = 0; i < 10; i++) {
			recordsInstrument1[i] = createRecord(CommonTest.INSTRUMENT1, randonmDateGenerator(),
					randomDoubleValueGenerator());
		}
	}

	private static void createRecordsForInstrument2() {
		for (int i = 0; i < 10; i++) {
			recordsInstrument2[i] = createRecord(CommonTest.INSTRUMENT2, randonmDateGenerator(),
					randomDoubleValueGenerator());
		}
	}

	private static void createRecordsForInstrument3() {
		for (int i = 0; i < 10; i++) {
			recordsInstrument3[i] = createRecord(CommonTest.INSTRUMENT3, randonmDateGenerator(),
					randomDoubleValueGenerator());
		}
	}

	private static Date randonmDateGenerator() {
		long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
		Date date = new Date(random);
		return date;
	}

	private static Double randomDoubleValueGenerator() {
		Double random = ThreadLocalRandom.current().nextDouble(1.00, 2.00);
		return random;
	}

	public static Double meanOfInstrument1() {
		Double mean = 0.0;
		Double total = 0.0;
		int recCount = recordsInstrument1.length;
		for (int i = 0; i < recordsInstrument1.length; i++) {
			Record record = recordsInstrument1[i];
			if (DateUtil.isNonBusinessDate(record.getReadingDate())) {
				logger.error("skipping record from inside test data as a non business day: " + record.toString());
				recCount = recCount - 1;
				continue;
			}
			total = total + record.getValue();
		}
		mean = total / recCount;
		return mean;
	}

	public static Double meanOfInstrument2() {
		Double mean = 0.0;
		Double total = 0.0;
		int recCount = recordsInstrument2.length;
		for (int i = 0; i < recordsInstrument2.length; i++) {
			Record record = recordsInstrument2[i];
			if (DateUtil.isNonBusinessDate(record.getReadingDate())) {
				logger.error("skipping record from inside test data as a non business day: " + record.toString());
				recCount = recCount - 1;
				continue;
			}
			total = total + record.getValue();
		}
		mean = total / recCount;
		return mean;
	}

	public static Double meanOfInstrument3() {
		Double mean = 0.0;
		Double total = 0.0;
		int recCount = recordsInstrument3.length;
		for (int i = 0; i < recordsInstrument3.length; i++) {
			Record record = recordsInstrument3[i];
			if (DateUtil.isNonBusinessDate(record.getReadingDate())) {
				logger.error("skipping record from inside test data as a non business day: " + record.toString());
				recCount = recCount - 1;
				continue;
			}
			total = total + record.getValue();
		}
		mean = total / recCount;
		return mean;
	}

	public static void destroyData() {
		recordsInstrument1 = null;

		recordsInstrument2 = null;

		recordsInstrument3 = null;
	}
}
