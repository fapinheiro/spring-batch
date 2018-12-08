package br.com.batch.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import br.com.batch.entity.Transaction;

public class TransactionWriterListener implements ItemWriteListener<Transaction> {

	private static final Logger LOG = LoggerFactory.getLogger(TransactionWriterListener.class);
	
	@Override
	public void beforeWrite(List<? extends Transaction> items) {
		// TODO Auto-generated method stub
		LOG.info("Writing transaction..." + items.size());
	}

	@Override
	public void afterWrite(List<? extends Transaction> items) {
		// TODO Auto-generated method stub
		LOG.info("Wrote transaction: " + items.size());
	}

	@Override
	public void onWriteError(Exception ex, List<? extends Transaction> items) {
		// TODO Auto-generated method stub
		LOG.error("Error writing transaction: " + items.size(), ex);
	}

}
