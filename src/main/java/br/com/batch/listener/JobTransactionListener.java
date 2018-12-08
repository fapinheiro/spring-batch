package br.com.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.batch.entity.Statistic;
import br.com.batch.entity.Transaction;

@Component
public class JobTransactionListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(JobTransactionListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobTransactionListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// Ok, nothing
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) { // Catch job status
			
			// Select and output each data inserted in the database
			LOG.info("JOB " + jobExecution.getJobInstance().getJobName() + " completou");
			jdbcTemplate
			.query("SELECT id, username, userid, transaction_amount, transaction_iof FROM transaction",
				(rs, row) -> new Transaction(
						rs.getInt(1),
						rs.getString(2),
					rs.getString(3),
					rs.getDouble(4),
					rs.getDouble(5))
			).forEach(transaction -> LOG.info("Transacao <" + transaction + "> inserida no database."));
			
			// Select and output each data inserted in the database
			jdbcTemplate
			.query(
				"SELECT id, username, sumvalue FROM statistic",
				(rs, row) -> new Statistic(
						rs.getInt(1),
						rs.getString(2), 
						rs.getDouble(3))
			).forEach(statistic -> LOG.info("Statistic <" + statistic + "> inserida no database."));

		} else {
			
			// Check if database was rolledback
			jdbcTemplate
			.query(
				"SELECT count(*) FROM transaction",
				(rs, row) -> rs.getString(1))
			.forEach(qtd -> LOG.info("Transacoes <" + qtd + "> inserida no database."));
			
			// 
			jdbcTemplate
			.query(
				"SELECT count(*) FROM statistic",
				(rs, row) -> rs.getString(1))
			.forEach(qtd -> LOG.info("Statistic <" + qtd + "> inserida no database."));
		}
	}
}
