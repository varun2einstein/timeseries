package com.timeseries.db;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timeseries.CommonTest;
import com.timeseries.db.ModifierDao;

public class TestModifierDao{

	private static ModifierDao modifierDao= new ModifierDao();
	
	@BeforeClass
	public static void init() throws SQLException, IOException {
		CommonTest.init();
	}
	
	@Test
	public void testGetModifierForInstrument() throws SQLException {
		Double value=modifierDao.getModifierForInstrument(CommonTest.INSTRUMENT1);
		assertTrue(CommonTest.properties.getProperty(CommonTest.INSTRUMENT1)!=null);
		assertTrue(value==Double.parseDouble(CommonTest.properties.getProperty(CommonTest.INSTRUMENT1)));
	}
	
	@AfterClass
	public static void destroy() throws SQLException {
		CommonTest.destroy();
	}
}
