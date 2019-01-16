package br.com.batch.configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import br.com.batch.entity.Statistic;
import br.com.batch.entity.Transaction;
import br.com.batch.listener.FileListener;
import br.com.batch.listener.JDBCListener;
import br.com.batch.listener.JobTransactionListener;
import br.com.batch.listener.StepListener;
import br.com.batch.processor.FileProcessor;
import br.com.batch.reader.FileReader;
import br.com.batch.reader.JDBCReader;
import br.com.batch.tasklet.TaskletExample;
import br.com.batch.writer.FileWriter;
import br.com.batch.writer.JDBCWriter;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(JobBatchConfiguration.class);
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobConfig jobConfig;
    
    @Autowired
    @Qualifier("sqLiteDB")
    public DataSource h2DB;
    
    // @Autowired
    // @Qualifier("mysqlDB")
    // public DataSource mysqlDB;

    private Resource outputResource = new FileSystemResource("output/transactions-output.csv");

    public DefaultTransactionAttribute attribute;

    @PostConstruct
    public void init() {
    	
    	// Show properties
		LOG.debug("jobConfig {}", jobConfig);
    	
    	// Use it only and not using an @Service and @Repository with @Transactional
    	attribute = new DefaultTransactionAttribute();
        attribute.setPropagationBehavior(Propagation.REQUIRED.value());
        attribute.setIsolationLevel(Isolation.SERIALIZABLE.value());
        attribute.setTimeout(jobConfig.getThreads().get("timeout"));
    }
    
    @Bean
    public Job importTransactionJob(JobTransactionListener jobListener) {
    	return jobBuilderFactory.get("JobTransaction")
    			.incrementer(new RunIdIncrementer())
                .listener(jobListener) // Defines job listener
                .start(stepTasklet())// To run only 1 .flow(step1).end() // To run more than one .start(step1).next(step2).next(stepN) 
    			.on("FAILED").end() // .end() Ends the job execution on .flow()
                .next(stepFile())
                .from(stepFile()).on("COMPLETED").to(stepDataBase()).end() // Only execute second step if the first executed correctly without skips
    			.build();
    }
    
    // STEPS

    /**
     * Used to process a single task. Do not use for writing to file or inserting to database, use Steps for that purpouse
     * @return
     */
	@Bean 
	public Step stepTasklet() {
		return stepBuilderFactory.get("stepTasklet")
			.tasklet(new TaskletExample("teste"))
			.build();
    }
    
    // STEPS
    /**
     * Reads a flat file and write to another flat file
     * @return
     */
    @Bean
    public Step stepFile() {
    	return stepBuilderFactory.get("StepFile")
    			.<Transaction, Transaction> chunk(jobConfig.getThreads().get("count")) // How much data to write at a time. Paralellal process. 
    			.reader(fileReader())
    			.processor(fileProcessor()) // Optinal With Processor we can transform chunk in/out
    			.writer(fileWriter())
    			.listener(stepListener()) // Optional
                .listener((ItemReadListener<Transaction>)fileListener()) // Optional
                .listener((ItemProcessListener<Transaction, Transaction>)fileListener())
    			.listener((ItemWriteListener<Transaction>)fileListener()) // Optinal
    			.faultTolerant()
    			//.noRollback(Exception.class) // Optional, exceptions that does not causes rollback
    			.skipLimit(jobConfig.getThreads().get("skip-limit"))
    			.skip(Exception.class) // Exceptions that causes skipping. Including subclasses.
    			//.noSkip(type) // Exception stop skipping
    			.transactionAttribute(attribute) // Define Transaction scope
    			.build();
    }
    
    /**
     * Reads a database e writes to database
     * @return
     */
    @Bean
    public Step stepDataBase() {
    	return stepBuilderFactory.get("StepDataBase")
    			.<Statistic, Statistic> chunk(jobConfig.getThreads().get("count")) // How much data to write at a time 
    			.reader(databaseJDBCReader())
    			//.processor(transactionProcessor()) // Optinal, With Processor we can transform chunk in/out
    			.writer(statisticJDBCWriter())
    			.listener(stepListener()) // Optional
    			.listener((ItemReadListener<Statistic>)jdbcListener()) // Optional
    			.listener((ItemWriteListener<Statistic>)jdbcListener()) // Optional
    			.faultTolerant()
    			//.noRollback(Exception.class) // Optional, exceptions that does not causes rollback
    			.skipLimit(jobConfig.getThreads().get("skip-limit"))
    			.skip(Exception.class) // Exceptions that causes skipping. Including subclasses.
    			//.noSkip(Exception.class) // Exception without skipping
    			.transactionAttribute(attribute) // Define Transaction scope
    			.build();
    }
    
    // READERS
    @Bean
    public FlatFileItemReader<Transaction> fileReader() {
    	return new FileReader().getReader();
    }

    @Bean
    public JdbcCursorItemReader<Statistic> databaseJDBCReader() {
            return new JDBCReader().getJDBCReader(
                h2DB,
                jobConfig.getLimits().get("validade"),
    			jobConfig.getLimits().get("max-result"), 
    			jobConfig.getThreads().get("timeout"));
    }

    // PROCESSORS
    @Bean
    public FileProcessor fileProcessor() {
        return new FileProcessor();
    }

    // WRITERS
    @Bean
    public FlatFileItemWriter<Transaction> fileWriter() {
    	return new FileWriter().getWriter(outputResource);
    }

    @Bean
    public JdbcBatchItemWriter<Statistic> statisticJDBCWriter() {
    	return new JDBCWriter().getJDBCWriter(h2DB);
    }

    // LISTENERS
    @Bean
    public StepListener stepListener() {
    	return new StepListener(jobConfig.getThreads().get("skip-limit"));
    }
    
    @Bean
    public FileListener fileListener() {
    	return new FileListener();
    }   
    
    @Bean
    public JDBCListener jdbcListener() {
    	return new JDBCListener();
    }  
    
}
