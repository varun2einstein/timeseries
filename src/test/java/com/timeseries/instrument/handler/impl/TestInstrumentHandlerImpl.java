package com.timeseries.instrument.handler.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.timeseries.CommonTest;
import com.timeseries.algo.Algorithm;
import com.timeseries.algo.impl.MeanCalcAlgorithm;
import com.timeseries.data.RecordTestData;
import com.timeseries.model.Record;

public class TestInstrumentHandlerImpl {

	private static InstrumentHandlerImpl instruHandler;
	private static Algorithm algorithm;
	@BeforeClass
	public static void init() throws SQLException, IOException {
		CommonTest.init();
		instruHandler= new InstrumentHandlerImpl();
		algorithm= new MeanCalcAlgorithm();
		instruHandler.setAlgorithm(algorithm);
	}
	
	public void testHandler() {
		for(int i=0; i<RecordTestData.recordsInstrument1.length;i++) {
			Record record= RecordTestData.recordsInstrument1[i];
			instruHandler.handle(record);
		}
		
	}
	
	@AfterClass
	public static void destroy() throws SQLException {
		CommonTest.destroy();
		instruHandler= null;
		algorithm= null;
	}
}
