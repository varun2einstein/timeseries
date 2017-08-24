package com.timeseries.db;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;

import com.timeseries.AbstractTest;

public class TestDBSetup extends AbstractTest{

	private static final String GET_INSTRUMENT_MODIFIERS_COUNT="SELECT COUNT(*) from INSTRUMENT_PRICE_MODIFIER";
	
	@Before
	public void testloadInstrumentPriceModifiers() throws IOException, SQLException {
		dbSetup.loadInstrumentPriceModifiers();
		PreparedStatement ps= dbSetup.getConnection().prepareStatement(GET_INSTRUMENT_MODIFIERS_COUNT);
		ResultSet rs=ps.executeQuery();
		int rowCount=0;
		while(rs.next()) {
			rowCount=rs.getInt(1);
		}
		assertTrue(rowCount==3);
	}
}
