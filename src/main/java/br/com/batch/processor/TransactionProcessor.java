package br.com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.batch.entity.Transaction;

/*
 * Recebe uma transacao e calcula a taxa do iof 
 */
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {

	//private static final Logger LOG = LoggerFactory.getLogger(TransactionProcessor.class);
	
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
