package br.com.batch.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

import br.com.batch.entity.Statistic;

public class StatisticWriter {
	
	public JdbcBatchItemWriter<Statistic> getJDBCWriter(DataSource dataSource) {
    	final String sql =
    			"INSERT INTO statistic (username, sumvalue) "
    			+ "VALUES (?,?)";
        return new JdbcBatchItemWriterBuilder<Statistic>()
            .itemPreparedStatementSetter(  // Using lamba to map fields
            		(statistic, preparedStatement) -> {
            			preparedStatement.setString(1, statistic.getUsername());
            			preparedStatement.setDouble(2, statistic.getSumValue());
             })
            .sql(sql)
            .dataSource(dataSource)
            .build();
    }
}
