package com.timeseries.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timeseries.CommonTest;
import com.timeseries.data.RecordTestData;
import com.timeseries.instrument.handler.impl.InstrumentHandlerImpl;
import com.timeseries.model.Record;

public class TestRecordHandler {

	private static final RecordHandler recordHandler= new RecordHandler();

	@BeforeClass
	public static void init() throws SQLException, IOException {
		CommonTest.init();
	}
	
	@Test
	public void testSubmitRecord() {
		for(Record record:RecordTestData.recordsInstrument1) {
			recordHandler.submit(record);
		}
		InstrumentHandlerImpl actualHandler=(InstrumentHandlerImpl)recordHandler.getHandlerMap().get(CommonTest.INSTRUMENT1);
		assertTrue(RecordTestData.meanOfInstrument1().compareTo(actualHandler.getValue().doubleValue())==0);
	}
	
	@AfterClass
	public static void destroy() throws SQLException {
		CommonTest.destroy();
	}

}
