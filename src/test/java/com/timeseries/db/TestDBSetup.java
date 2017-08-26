package com.timeseries.db;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timeseries.CommonTest;

public class TestDBSetup {
	
	private static final String GET_INSTRUMENT_MODIFIERS_COUNT="SELECT COUNT(*) from INSTRUMENT_PRICE_MODIFIER";

	@BeforeClass
	public static void init() throws SQLException, IOException {
		CommonTest.init();
	}
	
	@Test
	public void testloadInstrumentPriceModifiers() throws IOException, SQLException {
		PreparedStatement ps= CommonTest.dbSetup.getConnection().prepareStatement(GET_INSTRUMENT_MODIFIERS_COUNT);
		ResultSet rs=ps.executeQuery();
		int rowCount=0;
		while(rs.next()) {
			rowCount=rs.getInt(1);
		}
		assertTrue(rowCount==CommonTest.properties.size());
	}
	
	@AfterClass
	public static void destroy() throws SQLException {
		CommonTest.destroy();
	}
}
