package com.timeseries;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.timeseries.db.DBSetup;

public abstract class AbstractTest {

	protected static DBSetup dbSetup= new DBSetup();
	private static final int TABLE_ALREADY_EXISTS=30000;
	
	@BeforeClass
	public static void init() throws SQLException {
		dbSetup.getConnection();
		
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
	
	@AfterClass
	public static void destroy() throws SQLException {
		dbSetup.dropTables();
		dbSetup.closeConnection();
	}
}
