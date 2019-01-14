package br.com.batch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.stereotype.Component;

/**
 * Configure spring batch to use one datasource to register jobs execution
 * 
 * @see JobRepositoryConfig.class
 * 
 * @author filipe.pinheiro, 03/01/2019
 */
@Component
public class NoPersistenceBatchConfigurer extends DefaultBatchConfigurer {
    @Override
    public void setDataSource(DataSource dataSource) {
        // Ok, nothing here
    }
}