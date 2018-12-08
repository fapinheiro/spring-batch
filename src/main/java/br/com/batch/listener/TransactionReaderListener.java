package br.com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

import br.com.batch.entity.Transaction;

public class TransactionReaderListener implements ItemReadListener<Transaction>{

	private static final Logger LOG = LoggerFactory.getLogger(TransactionReaderListener.class);
	
	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
		LOG.info("Reading transaction...");
	}

	@Override
	public void afterRead(Transaction item) {
		// TODO Auto-generated method stub
		LOG.info("Read " + item.getUsername());
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		LOG.error("Error reading transaction", ex);
	}

}
