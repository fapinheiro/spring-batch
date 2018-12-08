package br.com.batch.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import br.com.batch.entity.Statistic;

public class StatisticWriterListener implements ItemWriteListener<Statistic> {

	private static final Logger LOG = LoggerFactory.getLogger(StatisticWriterListener.class);
	
	@Override
	public void beforeWrite(List<? extends Statistic> items) {
		// TODO Auto-generated method stub
		LOG.info("Writing statistic..." + items.size());
	}

	@Override
	public void afterWrite(List<? extends Statistic> items) {
		// TODO Auto-generated method stub
		LOG.info("Wrote statistic: " + items.size());
	}

	@Override
	public void onWriteError(Exception ex, List<? extends Statistic> items) {
		// TODO Auto-generated method stub
		LOG.error("Error writing statistic: " + items.size(), ex);
	}

}
