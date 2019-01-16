package br.com.batch.listener;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;

import br.com.batch.entity.Statistic;


/**
 * Listener do JDBCListener para capturar eventos no Read, Process e Write.
 * 
 * @author filipe.pinheiro, 03/01/2019
 */
public class JDBCListener implements 
    ItemReadListener<Statistic>, 
    ItemWriteListener<Statistic> {
    
    private Logger LOG = LoggerFactory.getLogger(FileListener.class);

    // READ LISTENERS
    @Override
    public void beforeRead() {
        // Ok, nothing here.
    }
    
    @Override
    public void afterRead(Statistic item) {
        LOG.debug("Lido Statistic {}", item);
    }
    
    @Override
    public void onReadError(Exception ex) {
        LOG.error("Erro ao ler Statistic", ex);
    }

    // PROCESS LISTENER
    // Ok, nothing here

    // WRITER LISTENERS
    @Override
    public void beforeWrite(List<? extends Statistic> items) {
        // Ok, nothing here.
    }

    @Override
    public void afterWrite(List<? extends Statistic> items) {
        items.stream().forEach( item -> afterWrite(item) );
    }

    private void afterWrite(Statistic item) {
        LOG.debug("Atualizado Statistic {}", item);
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Statistic> items) {
        items.stream().forEach( item -> afterWrite(item) );
        LOG.error("Erro ao atualizar Statistic", exception);
    }
}