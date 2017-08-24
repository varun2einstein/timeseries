package com.timeseries.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.timeseries.util.Queries;

public class ModifierDao extends DBSetup{
	
	public Double getModifierForInstrument(String modifierName) throws SQLException {
		PreparedStatement ps=getConnection().prepareStatement(Queries.GET_MODIFIER_FOR_INSTRUMENT);
		ResultSet rs=ps.executeQuery();
		Double modifier=rs.getDouble(1);
		return modifier;
	}
}
