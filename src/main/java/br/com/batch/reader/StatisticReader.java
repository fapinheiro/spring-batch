package br.com.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;

import br.com.batch.entity.Statistic;
import br.com.batch.mapper.StatisticRowMapper;


public class StatisticReader  {
    
    public JdbcCursorItemReader<Statistic> getJDBCReader(DataSource dataSource) {
            return new JdbcCursorItemReaderBuilder<Statistic>()
                            .dataSource(dataSource)
                            .name("transactionJDBCReader")
                            .sql("select username, sum(transaction_amount) as sumvalue from transaction group by username")
                            .rowMapper(new StatisticRowMapper())
                            .build();

    }
}
