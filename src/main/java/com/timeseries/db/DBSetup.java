package com.timeseries.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.util.Queries;

public class DBSetup {

	protected Connection connection;
	private static final Logger logger = LoggerFactory.getLogger(ModifierDao.class);
	private static final String DBURL = "jdbc:derby:testdb;create=true";
	private static final String INSTRUMENT_PRICE_MODIFIER_PROPERTIES = "intrument-modifier.properties";

	public void connect() throws SQLException {
		try {
			connection = DriverManager.getConnection(DBURL);
			logger.info("database connection established");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			logger.error(e.getCause().getMessage());
			throw e;
		}
	}

	public Connection getConnection() throws SQLException {
		if(connection==null) {
			connect();
		}
		return connection;
	}

	public void initTables() throws SQLException {
		PreparedStatement ps = connection.prepareStatement(Queries.CREATE_INSTRUMENT_PRICE_MODIFIER,
				Statement.RETURN_GENERATED_KEYS);
		ps.executeUpdate();
	}

	public void insertRowInstrumentPriceModifier(String instrumentName, Double value) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(Queries.INSERT_INSTRUMENT_PRICE_MODIFIER,
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, instrumentName);
		ps.setDouble(2, value);
		ps.executeUpdate();
	}

	public void loadInstrumentPriceModifiers() throws IOException, SQLException {
		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader().getResourceAsStream(INSTRUMENT_PRICE_MODIFIER_PROPERTIES));

		for (Entry entry : properties.entrySet()) {
			String instrumentName = (String) entry.getKey();
			Double modifier = Double.parseDouble((String)entry.getValue());
			insertRowInstrumentPriceModifier(instrumentName, modifier);
		}
	}

	public void dropTables() throws SQLException {
		PreparedStatement ps = connection.prepareStatement(Queries.DROP_INSTRUMENT_PRICE_MODIFIER);
		ps.executeUpdate();
	}

	public void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

}
