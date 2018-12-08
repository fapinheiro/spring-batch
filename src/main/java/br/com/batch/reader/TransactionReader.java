package br.com.batch.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;

import br.com.batch.entity.Transaction;


public class TransactionReader  {

    public FlatFileItemReader<Transaction> getReader() {
    	
    	// Pode ler de um S3 ou de de outro server
        return new FlatFileItemReaderBuilder<Transaction>()
            .name("transactionReader")
            //.resource(new ClassPathResource("sample-data.csv"))
            .resource(new FileSystemResource("c:\\sample-data.csv"))
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
