package br.com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class StepListener extends StepExecutionListenerSupport {
    
    private static final Logger LOG = LoggerFactory.getLogger(StepListener.class);

	private int limit;
	
	public StepListener(int limit) {
		this.limit = limit;
	}
	
	@Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) &&
              stepExecution.getSkipCount() > limit) {
            LOG.info("Marking step as COMPLETED WITH SKIPS");
            return new ExitStatus("COMPLETED WITH SKIPS");
        }
        else {
            return null;
        }
    }
}