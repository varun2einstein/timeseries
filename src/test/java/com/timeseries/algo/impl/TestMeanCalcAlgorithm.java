package com.timeseries.algo.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timeseries.CommonTest;
import com.timeseries.data.RecordTestData;
import com.timeseries.model.Record;
import com.timeseries.util.DateUtil;

public class TestMeanCalcAlgorithm {

	static MeanCalcAlgorithm mca = null;

	@BeforeClass
	public static void init() throws SQLException, IOException {
		CommonTest.init();
	}
	
	@Before
	public void inittest() {
		mca = new MeanCalcAlgorithm();
	}

	@Test
	public void testProcessRecordForInstrument1() {
		for (int i = 0; i < RecordTestData.recordsInstrument1.length; i++) {
			Record record = RecordTestData.recordsInstrument1[i];
			if (!DateUtil.isNonBusinessDate(record.getReadingDate()))
				mca.process(record);
		}
		assertTrue(mca.currentMean.compareTo(RecordTestData.meanOfInstrument1()) == 0);
	}

	@Test
	public void testProcessRecordForInstrument2() {
		for (int i = 0; i < RecordTestData.recordsInstrument2.length; i++) {
			Record record = RecordTestData.recordsInstrument2[i];
			if (!DateUtil.isNonBusinessDate(record.getReadingDate()))
				mca.process(record);
		}
		assertTrue(mca.currentMean.compareTo(RecordTestData.meanOfInstrument2()) == 0);
	}

	@After
	public void destroyTest() {
		mca = null;
	}
	
	@AfterClass
	public static void destroy() throws SQLException {
		CommonTest.destroy();
	} 
}
