package br.com.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;

import br.com.batch.entity.Statistic;

/**
 * Reader designed for reading database with jdbc
 * 
 * @author filipe.pinheiro, 12/12/2018
 */
public class JDBCReader  {
    
    private static final String SQL = 
        "select username, sum(transaction_amount) as sumvalue from transactions group by username";
            
    public JdbcCursorItemReader<Statistic> getJDBCReader(
            DataSource dataSource, int expire, int limit, int timeout) {
            return new JdbcCursorItemReaderBuilder<Statistic>()
                    // .preparedStatementSetter( st -> {
                    //     st.setInt(1, expire);
                    //     st.setInt(2, StatusVendaEnum.AGUARDANDO.ordinal());
                    //     st.setInt(3, limit);
                    // })
                    .queryTimeout(timeout)
                    .dataSource(dataSource)
                    .name("DataBaseJDBCReader")
                    .sql(SQL)
                    .rowMapper( (rs, rowNum) -> {
                        Statistic st = new Statistic();
                        st.setId(rs.getInt("id"));
                        st.setSumValue(rs.getDouble("sumvalue"));
                        st.setUsername(rs.getString("username"));
                        return st;
                    })
                    .build();
    }
}
