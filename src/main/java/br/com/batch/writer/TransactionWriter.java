package br.com.batch.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

import br.com.batch.entity.Transaction;

public class TransactionWriter {
	
	public JdbcBatchItemWriter<Transaction> getWriter(DataSource dataSource) {
    	final String sql =
    			"INSERT INTO transaction (username, userid, transaction_date, transaction_amount, transaction_iof) "
    			+ "VALUES (:username, :userid, :transactionDate, :transactionAmount, :transactionIof)";
        return new JdbcBatchItemWriterBuilder<Transaction>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>()) // Map with bean property
            .sql(sql)
            .dataSource(dataSource)
            .build();
    }
	
}
