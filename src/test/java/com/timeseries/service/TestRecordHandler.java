package com.timeseries.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timeseries.CommonTest;
import com.timeseries.data.RecordTestData;
import com.timeseries.model.Record;

public class TestRecordHandler {

	private static final RecordHandler recordHandler = new RecordHandler();

	@BeforeClass
	public static void init() throws SQLException, IOException {
		CommonTest.init();
	}

	@Test
	public void testSubmitRecord() throws InterruptedException {
		for (Record record : RecordTestData.recordsInstrument1) {
			recordHandler.submit(record);
		}
		Record lastRecord = new Record();
		lastRecord.setLastRecord(true);
		recordHandler.submit(lastRecord);

		Double value = 0.00;
		while (true) {
			if(recordHandler.isProcessingDone()) {
				value = recordHandler.getCurrentValueForInstrument(CommonTest.INSTRUMENT1);
				break;
			}
		}

		assertTrue(RecordTestData.meanOfInstrument1().compareTo(value) == 0);

	}

	@AfterClass
	public static void destroy() throws SQLException {
		CommonTest.destroy();
	}

}
