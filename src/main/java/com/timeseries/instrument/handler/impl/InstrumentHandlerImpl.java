package com.timeseries.instrument.handler.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.algo.Algorithm;
import com.timeseries.db.ModifierDao;
import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.model.Record;

public class InstrumentHandlerImpl implements InstrumentHandler {

	private Algorithm algorithm;
	private Number value;
	private ModifierDao modifierDao;
	
	private static final Logger logger= LoggerFactory.getLogger(InstrumentHandlerImpl.class);
	
	public void handle(Record record) {
		try {
			record.setValue(record.getValue()*modifierDao.getModifierForInstrument(record.getInstrumentName()));
		} catch (SQLException e) {
			logger.error("Error getting modifier from the database: "+e.getMessage());
			throw new RuntimeException(e);
		}
		setValue(algorithm.process(record));
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}


	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

	public ModifierDao getModifierDao() {
		return modifierDao;
	}

	public void setModifierDao(ModifierDao modifierDao) {
		this.modifierDao = modifierDao;
	}

}
