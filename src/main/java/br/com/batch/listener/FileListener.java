package br.com.batch.listener;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;

import br.com.batch.entity.Transaction;


/**
 * Listener do FileListener para capturar eventos no Read, Process e Write.
 * 
 * @author filipe.pinheiro, 03/01/2019
 */
public class FileListener implements 
    ItemReadListener<Transaction>, 
    ItemProcessListener<Transaction, Transaction>,
    ItemWriteListener<Transaction> {
    
    private Logger LOG = LoggerFactory.getLogger(FileListener.class);

    // READ LISTENERS
    @Override
    public void beforeRead() {
        // Ok, nothing here.
    }
    
    @Override
    public void afterRead(Transaction item) {
        LOG.debug("Lido Transaction {}", item);
    }
    
    @Override
    public void onReadError(Exception ex) {
        LOG.error("Erro ao ler Transaction", ex);
    }

    // PROCESS LISTENER
    @Override
    public void beforeProcess(Transaction item) {
        // Ok, nothing here.
    }

    @Override
    public void afterProcess(Transaction item, Transaction result) {
        LOG.debug("Processado Transaction {}", item);
    }
    
    @Override
    public void onProcessError(Transaction item, Exception e) {
        LOG.debug("Erro ao processar Transaction {}", item);
        LOG.error("Erro ao processar Transaction", e);
    }

    // WRITER LISTENERS
    @Override
    public void beforeWrite(List<? extends Transaction> items) {
        // Ok, nothing here.
    }

    @Override
    public void afterWrite(List<? extends Transaction> items) {
        items.stream().forEach( item -> afterWrite(item) );
    }

    private void afterWrite(Transaction item) {
        LOG.debug("Atualizado Transaction {}", item);
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Transaction> items) {
        items.stream().forEach( item -> afterWrite(item) );
        LOG.error("Erro ao atualizar Transaction", exception);
    }
}