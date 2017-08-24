package com.timeseries.db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import com.timeseries.db.ModifierDao;

public class TestModifierDao extends TestDBSetup{

	private static ModifierDao modifierDao= new ModifierDao();
	
	@Test
	public void testGetModifierForInstrument() throws SQLException {
		Double value=modifierDao.getModifierForInstrument("INSTRUMENT1");
		assertTrue(value==2.00);
	}
}
