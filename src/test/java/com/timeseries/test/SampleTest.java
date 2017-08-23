package com.timeseries.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.timeseries.db.ModifierDao;

public class SampleTest {

	ModifierDao modifierDao= new ModifierDao();
	
	@Test
	public void test() throws SQLException {
		modifierDao.connect();
		Connection con= modifierDao.getConnection();
		modifierDao.initTables();
	}

}
