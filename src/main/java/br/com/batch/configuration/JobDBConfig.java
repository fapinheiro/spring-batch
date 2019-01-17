package br.com.batch.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.net.MalformedURLException;

import javax.sql.DataSource;

/**
 * Configure datasource used in job execution
 * 
 * @author filipe.pinheiro, 11/12/2018
 */
@Configuration
@EnableBatchProcessing
public class JobDBConfig {

    @Autowired
    private Environment env;

    @Value("org/springframework/batch/core/schema-h2.sql")
    private Resource rscSchemaH2;

    @Value("schema.sql")
    private Resource rscSchema;

    // DataBases
    @Primary
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(
            env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }
 
    @Bean(name = "sqLiteDB")
    public DataSource sqLiteDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("job.batch.sqlite.driverClassName"));
        dataSource.setUrl(env.getProperty("job.batch.sqlite.url"));
        dataSource.setUsername(env.getProperty("job.batch.sqlite.username"));
        dataSource.setPassword(env.getProperty("job.batch.sqlite.password"));
        return dataSource;
    }

    // @Bean(name = "oracleDB")
	// public DataSource oracleDataSource() {
    //     DriverManagerDataSource dataSource = new DriverManagerDataSource();
    //     dataSource.setDriverClassName(
    //         env.getProperty("job.ecom.oracle.driverClassName"));
    //     dataSource.setUrl(env.getProperty("job.ecom.oracle.url"));
    //     dataSource.setUsername(env.getProperty("job.ecom.oracle.username"));
    //     dataSource.setPassword(env.getProperty("job.ecom.oracle.password"));
    //     return dataSource;
	// }

    // @Bean(name = "mysqlDB")
	// @ConfigurationProperties(prefix = "job.ecom.mysql")
	// public DataSource mysqlDataSource() {
	// 	return DataSourceBuilder.create().build();
	// }	

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource)
      throws MalformedURLException {
        ResourceDatabasePopulator databasePopulator = 
          new ResourceDatabasePopulator();
 
       //databasePopulator.addScript(rscSchemaH2);
        //databasePopulator.addScript(rscSchema);
        databasePopulator.setIgnoreFailedDrops(true);
 
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
 
        return initializer;
    }

    @Bean
    public DataSourceInitializer h2DataSourceInitializer(@Qualifier("sqLiteDB") DataSource dataSource)
      throws MalformedURLException {
        ResourceDatabasePopulator databasePopulator = 
          new ResourceDatabasePopulator();
 
       //databasePopulator.addScript(rscSchemaH2);
        databasePopulator.addScript(rscSchema);
        databasePopulator.setIgnoreFailedDrops(true);
 
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
 
        return initializer;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier(value="sqLiteDB") DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }
}
