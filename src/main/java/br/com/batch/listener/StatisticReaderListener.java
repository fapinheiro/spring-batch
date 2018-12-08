package br.com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

import br.com.batch.entity.Statistic;

public class StatisticReaderListener implements ItemReadListener<Statistic>{

	private static final Logger LOG = LoggerFactory.getLogger(StatisticReaderListener.class);
	
	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
		LOG.info("Reading statistic...");
	}

	@Override
	public void afterRead(Statistic item) {
		// TODO Auto-generated method stub
		LOG.info("Read " + item.getUsername());
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		LOG.error("Error reading statistic", ex);
	}

}
