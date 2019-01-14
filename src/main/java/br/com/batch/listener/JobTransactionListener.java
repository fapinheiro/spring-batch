package br.com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;


/**
 * Listener inicio e fim da execucao do job
 * 
 * @author filipe.pinheiro, 03/01/2019
 */
@Component
public class JobTransactionListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(JobTransactionListener.class);

	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOG.info("================ INITIALIZING JOB ECOMMERCE ===============");
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		LOG.info("================ ENDING JOB ECOMMERCE ===============");

		// Job Executed
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) { 
			// Do somenthing
		}
	}
	
}
