package br.com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.batch.entity.Transaction;

/**
 * Proccess and sets transaction's taxes
 * 
 * @author filipe.pinheiro - 14/01/2019
 */
public class FileProcessor implements ItemProcessor<Transaction, Transaction> {
	
	@Override
	public Transaction process(Transaction t) throws Exception {
		double amount = t.getTransactionAmount();
//		try {
//			Integer.parseInt(t.getTransactionAmount() );
//		} catch (NumberFormatException nfe) {
//			LOG.error("Invalid amount", nfe);
//		}
		double tax = amount > 2000 ? amount * 0.02 : amount * 0.01;
		//LOG.info(String.format("Gerando taxa: %.2f, para %s", tax, t.getUsername()));
		t.setTransactionIof( tax );
		return t;
	}

}
