package com.timeseries.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.algo.Algorithm;
import com.timeseries.algo.impl.MeanCalcAlgorithm;
import com.timeseries.algo.impl.MeanInDateRangeAlgorithm;
import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.instrument.handler.impl.InstrumentHandlerImpl;
import com.timeseries.util.DateUtil;

public class InstrumentHandlerLoader {

	private Map<String, InstrumentHandler> instrumentHandlerMapping = new HashMap<>();
	private static final String INSTRUMENT1 = "INSTRUMENT1";
	private static final String INSTRUMENT2 = "INSTRUMENT2";
	private static final String INSTRUMENT3 = "INSTRUMENT3";

	private static final Logger LOGGER= LoggerFactory.getLogger(InstrumentHandlerLoader.class);
	
	public InstrumentHandlerLoader(){
		try {
			initInstrumentHandlerMap();
		} catch (ParseException e) {
			LOGGER.error("Error occured while initializing InstumentHandlers"+e.getMessage());
		}
	}
	
	public Map<String, InstrumentHandler> getInstrumentHandlerMapping() {
		return instrumentHandlerMapping;
	}



	public void setInstrumentHandlerMapping(Map<String, InstrumentHandler> instrumentHandlerMapping) {
		this.instrumentHandlerMapping = instrumentHandlerMapping;
	}



	public void initInstrumentHandlerMap() throws ParseException {
		instrumentHandlerMapping.put(INSTRUMENT1, createInstrument1Handler());
		instrumentHandlerMapping.put(INSTRUMENT2, createInstrument2Handler());
		instrumentHandlerMapping.put(INSTRUMENT3, createInstrument3Handler());
	}

	private InstrumentHandler createInstrument3Handler() {
		Algorithm mcalcAlgo = new MeanCalcAlgorithm();
		return createInstrumentHandler(mcalcAlgo);
	}

	private InstrumentHandler createInstrumentHandler(Algorithm algorithm) {
		InstrumentHandlerImpl instrumentHandler = new InstrumentHandlerImpl();
		instrumentHandler.setAlgorithm(algorithm);
		return instrumentHandler;
	}

	private InstrumentHandler createInstrument1Handler() {
		Algorithm mcalcAlgo = new MeanCalcAlgorithm();
		return createInstrumentHandler(mcalcAlgo);
	}

	private InstrumentHandler createInstrument2Handler() throws ParseException {
		MeanInDateRangeAlgorithm mcalcAlgo = new MeanInDateRangeAlgorithm();
		return createInstrumentHandler(mcalcAlgo);
	}

}
