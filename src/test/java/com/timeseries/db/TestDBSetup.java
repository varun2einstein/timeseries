package com.timeseries.db;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDBSetup {

	private static DBSetup dbSetup= new DBSetup();
	private static final String GET_INSTRUMENT_MODIFIERS_COUNT="SELECT COUNT(*) from INSTRUMENT_PRICE_MODIFIER";
	private static final int TABLE_ALREADY_EXISTS=30000;
	
	@BeforeClass
	public static void init() throws SQLException {
		dbSetup.connect();
		
		try {
			dbSetup.initTables();
		} catch (SQLException e) {
			if(e.getErrorCode()==TABLE_ALREADY_EXISTS) {
				dbSetup.dropTables();
				dbSetup.initTables();
			}else {
				throw e;
			}
		}
	}
	
	@Test
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

	@AfterClass
	public static void destroy() throws SQLException {
		dbSetup.dropTables();
		dbSetup.closeConnection();
	}


}
