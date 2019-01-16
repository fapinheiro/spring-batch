package br.com.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author filipe.pinheiro 27/12/2018
 * 
 * An example of Tasklet
 */
public class TaskletExample implements Tasklet, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(Tasklet.class);

    private String jobConfig;

    public TaskletExample(String jobConfig) {
        this.jobConfig = jobConfig;
    }

    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {

        LOG.info("Tasklet logging...");

        // Code here
        
        return RepeatStatus.FINISHED;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(jobConfig, "jobConfig nao pode ser vazio");
    }

}