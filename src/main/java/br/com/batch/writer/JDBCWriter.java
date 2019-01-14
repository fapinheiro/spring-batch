package br.com.batch.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

import br.com.batch.entity.Statistic;

/**
 * JDBC writer for writting to the database
 * 
 * @author filipe.pinheiro 03/01/2019
 */
public class JDBCWriter {
    
    final String sql =
    			"insert into statisticss (username, sumvalue) VALUES (?,?)";
                
	public JdbcBatchItemWriter<Statistic> getJDBCWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Statistic>()
            .itemPreparedStatementSetter(  // Using lamba to map fields
            		(st, pstmt) -> {
            			pstmt.setString(1, st.getUsername());
            			pstmt.setDouble(2, st.getSumValue());
             })
            .sql(sql)
            .dataSource(dataSource)
            .build();
    }
}
