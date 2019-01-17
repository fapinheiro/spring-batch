package br.com.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import br.com.batch.entity.Transaction;

/**
 * @author filipe.pinheiro 27/12/2018
 * 
 * An example of Tasklet
 */
public class TaskletExample implements Tasklet, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(Tasklet.class);

    private JdbcTemplate jdbcTemplate;

    private String jobConfig;

    public TaskletExample(String jobConfig, JdbcTemplate jdbcTemplate) {
        this.jobConfig = jobConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {

        LOG.info("Tasklet logging...");

        // Code here
        jdbcTemplate.query("select * from transactions",
            (rs, row) -> {
                Transaction t = new Transaction();
                t.setId(rs.getInt(1));
                t.setUsername(rs.getString(2));
                return t;
            }).forEach((t) -> LOG.info(String.format("======== Lido transactions %d, %s", t.getId(), t.getUsername())));

        return RepeatStatus.FINISHED;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(jobConfig, "jobConfig nao pode ser vazio");
        Assert.notNull(jdbcTemplate, "jdbcTemplate nao pode ser vazio");
    }

}