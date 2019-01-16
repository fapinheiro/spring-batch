package br.com.batch.writer;


import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import br.com.batch.entity.Transaction;

public class FileWriter {

    @Bean
    public FlatFileItemWriter<Transaction> getWriter(Resource outputResource)
    {
        //Create writer instance
        FlatFileItemWriter<Transaction> writer = new FlatFileItemWriter<>();
         
        //Set output file location
        writer.setResource(outputResource);
         
        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);
         
        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<Transaction>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Transaction>() {
                    {
                        setNames(new String[] { 
                            "id", "username", "userid", "transactionDate", "transactionAmount", "transactionIof" });
                    }
                });
            }
        });
        return writer;
    }
	
}
