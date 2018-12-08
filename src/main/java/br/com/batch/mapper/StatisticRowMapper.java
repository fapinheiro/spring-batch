package br.com.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.batch.entity.Statistic;

public class StatisticRowMapper implements RowMapper<Statistic>{

    public static final String NAME_COLUMN = "username";
    public static final String SUM_COLUMN = "sumvalue";
    
	@Override
	public Statistic mapRow(ResultSet rs, int rowNum) throws SQLException {
		Statistic Statistic = new Statistic();
		
		Statistic.setUsername(rs.getString(NAME_COLUMN));
		Statistic.setSumValue(rs.getDouble(SUM_COLUMN));
		
        return Statistic;
	}

}
