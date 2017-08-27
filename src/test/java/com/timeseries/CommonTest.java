package com.timeseries;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.data.RecordTestData;
import com.timeseries.db.DBSetup;

public class CommonTest{

	public static DBSetup dbSetup= new DBSetup();
	private static final int TABLE_ALREADY_EXISTS=30000;
	private static final String INSTRUMENT_PRICE_MODIFIER_PROPERTIES = "intrument-modifier.properties";
	public static Properties properties= new Properties();
	public static final String INSTRUMENT1="INSTRUMENT1";
	public static final String INSTRUMENT2="INSTRUMENT2";
	public static final String INSTRUMENT3="INSTRUMENT3";

	protected static final Logger logger= LoggerFactory.getLogger(CommonTest.class);
	
	static {
		try {
			properties.load(CommonTest.class.getClassLoader().getResourceAsStream(INSTRUMENT_PRICE_MODIFIER_PROPERTIES));
		} catch (IOException e) {
			logger.error("Error loading default instrument price modifier properties: "+e.getMessage());
		}
	}
	
	public static void init() throws SQLException, IOException {
		dbSetup.getConnection();
		
		try {
			dbSetup.initTables();
			dbSetup.loadInstrumentPriceModifiers();
			
		} catch (SQLException e) {
			if(e.getErrorCode()==TABLE_ALREADY_EXISTS) {
				dbSetup.dropTables();
				dbSetup.initTables();
			}else {
				throw e;
			}
		}catch(IOException e) {
			logger.error("Error setting up tables "+ e.getMessage());
			throw e;
		}
		RecordTestData.initData();
		RecordTestData.createTestData();
	}
	
	
	public static void destroy() throws SQLException {
		dbSetup.dropTables();
		dbSetup.closeConnection();
		RecordTestData.destroyData();
	
	}
}
