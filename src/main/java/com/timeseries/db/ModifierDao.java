package com.timeseries.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifierDao {

	private Connection connection;
    private static final Logger logger= LoggerFactory.getLogger(ModifierDao.class);
    
	public void connect() throws SQLException {
		String dbUrl = "jdbc:derby:testdb;create=true";
		try {
			connection = DriverManager.getConnection(dbUrl);
			logger.info("database connection established");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			logger.error(e.getCause().getMessage());
			throw e;
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void initTables() throws SQLException {
		Statement statement= connection.createStatement();
		statement.executeUpdate("CREATE TABLE INSTRUMENT_PRICE_MODIFIER (ID INT PRIMARY KEY,NAME VARCHAR(50),MULTIPLIER DOUBLE)");
	}
}
