package com.timeseries.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.timeseries.util.Queries;

public class ModifierDao extends DBSetup{
	
	private static final Double DEFAULT_MODIFIER=1.00;
	
	private static final ModifierDao INSTANCE= new ModifierDao();
	
	private ModifierDao() {
		
	}
	
	public static ModifierDao getModifierDao() {
		return INSTANCE;
	}
	
	public Double getModifierForInstrument(String modifierName) throws SQLException {
		PreparedStatement ps=getConnection().prepareStatement(Queries.GET_MODIFIER_FOR_INSTRUMENT);
		ps.setString(1, modifierName);
		ResultSet rs=ps.executeQuery();
		Double modifier=DEFAULT_MODIFIER;
		while(rs.next()) {
		 modifier=rs.getDouble(1);
		}
		return modifier;
	}
}
