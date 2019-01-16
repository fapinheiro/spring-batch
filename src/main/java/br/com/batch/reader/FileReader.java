package br.com.batch.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ClassPathResource;

import br.com.batch.entity.Transaction;

/**
 * This is an example of file reader
 * 
 * @author filipe.pinheiro - 14/01/2019
 */
public class FileReader  {

    public FlatFileItemReader<Transaction> getReader() {
    	
    	// Pode ler de um S3 ou de de outro server
        return new FlatFileItemReaderBuilder<Transaction>()
            .name("transactionReader")
            .resource(new ClassPathResource("transactions-input.csv"))
            //.resource(new FileSystemResource("c:\\transactions-input.csv"))
            .linesToSkip(1) // Skip header
            .delimited() // Uses default delimiter
            .names(new String[]{"username", "userid", "transactionDate", "transactionAmount"})
            .fieldSetMapper(
            		new BeanWrapperFieldSetMapper<Transaction>() {
            			{
            				setTargetType(Transaction.class);
            			}
            		}
            )
            .build();
    }
    
}
