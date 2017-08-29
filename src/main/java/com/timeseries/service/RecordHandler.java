package com.timeseries.service;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timeseries.instrument.handler.InstrumentHandler;
import com.timeseries.instrument.handler.impl.InstrumentHandlerImpl;
import com.timeseries.model.Record;

public class RecordHandler {

	private static final Map<String, InstrumentHandler> handlerMap;
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordHandler.class);
	private static final int MAX_CAPACITY = 50;
	private final BlockingQueue<Task> recordQueue = new LinkedBlockingQueue<>(MAX_CAPACITY);
	private static final Logger logger = LoggerFactory.getLogger(RecordHandler.class);

	private volatile boolean stopProcessing = false;
	private volatile boolean isProcessingDone = false;

	static {
		InstrumentHandlerLoader loader = new InstrumentHandlerLoader();
		handlerMap = loader.getInstrumentHandlerMapping();
	}

	public RecordHandler() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Task task = null;
				while (!stopProcessing) {
					try {
						task = recordQueue.take();
						if (task.getRecord().isLastRecord()) {
							break;
						}
						task.run();
					} catch (InterruptedException e) {
						logger.error("Interrupted wile processing record " + (task == null ? "" : task.getRecord()));
						Thread.currentThread().interrupt();
					}
				}
				isProcessingDone = true;
				logger.debug("Terminating the internal active thread of record handler");
			}
		}).start();

	}

	public static Map<String, InstrumentHandler> getHandlerMap() {
		return handlerMap;
	}

	public boolean submit(final Record record) {
		try {
			Task task = new Task();
			task.setRecord(record);
			recordQueue.put(task);
		} catch (InterruptedException e) {
			logger.error("Interrupted while processing record :" + record.toString());
			Thread.currentThread().interrupt();
		}
		return true;
	}

	public Double getCurrentValueForInstrument(String instrumentName) {
		InstrumentHandler instruHandler = handlerMap.get(instrumentName);
		if (instruHandler == null) {
			LOGGER.error("Unable to fetch instrument handler for instrument " + instrumentName + " no mapping");
		}

		Double currentValue = ((InstrumentHandlerImpl) instruHandler).getValue().doubleValue();
		return currentValue;
	}

	public boolean isStopProcessing() {
		return stopProcessing;
	}

	public void setStopProcessing(boolean stopProcessing) {
		this.stopProcessing = stopProcessing;
	}

	public boolean isWorkQueueProcessed() {
		return recordQueue.isEmpty();
	}

	public boolean isProcessingDone() {
		return isProcessingDone;
	}

	public void setProcessingDone(boolean isProcessingDone) {
		this.isProcessingDone = isProcessingDone;
	}
}
