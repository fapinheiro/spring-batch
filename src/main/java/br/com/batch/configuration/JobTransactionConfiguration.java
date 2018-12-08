package br.com.batch.configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import br.com.batch.entity.Statistic;
import br.com.batch.entity.Transaction;
import br.com.batch.listener.JobTransactionListener;
import br.com.batch.listener.StatisticReaderListener;
import br.com.batch.listener.StatisticWriterListener;
import br.com.batch.listener.StepListener;
import br.com.batch.listener.TransactionReaderListener;
import br.com.batch.listener.TransactionWriterListener;
import br.com.batch.processor.TransactionProcessor;
import br.com.batch.property.JobProperty;
import br.com.batch.reader.StatisticReader;
import br.com.batch.reader.TransactionReader;
import br.com.batch.writer.StatisticWriter;
import br.com.batch.writer.TransactionWriter;

@Configuration
@EnableBatchProcessing
public class JobTransactionConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(JobTransactionConfiguration.class);
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobProperty jobProperty;
    
    @Autowired
    public DataSource dataSource;
    
    public DefaultTransactionAttribute attribute;

    @PostConstruct
    public void init() {
    	
    	// Show properties
    	String info = String.format("Iniciando JOB com %d threads", jobProperty.getThreads());
    	LOG.info(info);
    	info = String.format("Iniciando JOB com %d skipLimit", jobProperty.getSkipLimit());
    	LOG.info(info);
    	info = String.format("Iniciando JOB com %d timeout", jobProperty.getTimeout());
    	LOG.info(info);	
    	
    	// Use it only and not using an @Service and @Repository with @Transactional
    	attribute = new DefaultTransactionAttribute();
        attribute.setPropagationBehavior(Propagation.REQUIRED.value());
        attribute.setIsolationLevel(Isolation.SERIALIZABLE.value());
        attribute.setTimeout(jobProperty.getTimeout());
    }
    
    @Bean
    public Job importTransactionJob(JobTransactionListener jobListener) {
    	return jobBuilderFactory.get("JobTransaction")
    			.incrementer(new RunIdIncrementer())
    			.listener(jobListener) // Defines job listener
    			.start(stepTransaction()) // To run only 1 .flow(step1).end() // To run more than one .start(step1).next(step2).next(stepN) 
    			.on("FAILED").end() // .end() Ends the job execution on .flow()
    			.from(stepTransaction()).on("COMPLETED").to(stepStatistic()).end() // Only execute second step if the first executed correctly without skips
    			.build();
    }
    
    @Bean
    public Step stepTransaction() {
    	return stepBuilderFactory.get("StepTransaction")
    			.<Transaction, Transaction> chunk(jobProperty.getThreads()) // How much data to write at a time. Paralellal process. 
    			.reader(transactionReader())
    			.processor(transactionProcessor()) // Optinal With Processor we can transform chunk in/out
    			.writer(transactionWriter())
    			.listener(stepListener()) // Optional
    			.listener(transactionReaderListener()) // Optional
    			.listener(transactionWriterListener()) // Optinal
    			.faultTolerant()
    			//.noRollback(Exception.class) // Optional, exceptions that does not causes rollback
    			.skipLimit(jobProperty.getSkipLimit())
    			.skip(Exception.class) // Exceptions that causes skipping. Including subclasses.
    			//.noSkip(type) // Exception stop skipping
    			.transactionAttribute(attribute) // Define Transaction scope
    			.build();
    }
    
    @Bean
    public Step stepStatistic() {
    	return stepBuilderFactory.get("StepStatistic")
    			.<Statistic, Statistic> chunk(jobProperty.getThreads()) // How much data to write at a time 
    			.reader(statisticJDBCReader())
    			//.processor(transactionProcessor()) // Optinal, With Processor we can transform chunk in/out
    			.writer(statisticJDBCWriter())
    			.listener(stepListener()) // Optional
    			.listener(statisticReaderListener()) // Optional
    			.listener(statisticWriterListener()) // Optional
    			.faultTolerant()
    			//.noRollback(Exception.class) // Optional, exceptions that does not causes rollback
    			.skipLimit(jobProperty.getSkipLimit())
    			.skip(Exception.class) // Exceptions that causes skipping. Including subclasses.
    			//.noSkip(Exception.class) // Exception without skipping
    			.transactionAttribute(attribute) // Define Transaction scope
    			
    			.build();
    }
    
    @Bean
    public StepListener stepListener() {
    	return new StepListener(jobProperty.getSkipLimit());
    }
    
    @Bean
    public FlatFileItemReader<Transaction> transactionReader() {
    	return new TransactionReader().getReader();
    }

    @Bean
    public TransactionProcessor transactionProcessor() {
        return new TransactionProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Transaction> transactionWriter() {
    	return new TransactionWriter().getWriter(dataSource);
    }

    @Bean
    public JdbcCursorItemReader<Statistic> statisticJDBCReader() {
            return new StatisticReader().getJDBCReader(dataSource);
    }
    
    @Bean
    public JdbcBatchItemWriter<Statistic> statisticJDBCWriter() {
    	return new StatisticWriter().getJDBCWriter(dataSource);
    }
    
    @Bean
    public ItemReadListener<Transaction> transactionReaderListener() {
    	return new TransactionReaderListener();
    }
    
    @Bean
    public ItemWriteListener<Transaction> transactionWriterListener() {
    	return new TransactionWriterListener();
    }
    
    @Bean
    public ItemReadListener<Statistic> statisticReaderListener() {
    	return new StatisticReaderListener();
    }
    
    @Bean
    public ItemWriteListener<Statistic> statisticWriterListener() {
    	return new StatisticWriterListener();
    }
}
