package com.timeseries.db;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timeseries.db.ModifierDao;

public class TestModifierDao {

	private static ModifierDao modifierDao= new ModifierDao();
	
	@BeforeClass
	public static void init() throws SQLException {
		modifierDao.connect();
		modifierDao.initTables();
	}
	
	@Test
	public void test() throws SQLException {
		modifierDao.
	}

	@AfterClass
	public static void destroy() throws SQLException {
		modifierDao.dropTables();
		modifierDao.closeConnection();
	}
}
