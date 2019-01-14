# Example

- Read and write flat files
- Read and write from/to databases

# Spring Batch References
https://spring.io/guides/gs/batch-processing/
https://www.baeldung.com/introduction-to-spring-batch
https://docs.spring.io/spring-batch/3.0.x/reference/html/listOfReadersAndWriters.html
https://github.com/emalock3/spring-batch-example/blob/master/src/main/java/com/github/emalock3/spring/example/BatchConfiguration.java
https://github.com/spring-projects/spring-batch/tree/master/spring-batch-samples
https://docs.spring.io/spring-batch/4.1.x/reference/html/step.html

# Production References
Create jobs schema
https://www.mkyong.com/spring-batch/spring-batch-metadata-tables-are-not-created-automatically/
https://docs.spring.io/spring-batch/3.0.x/reference/html/metaDataSchema.html

# Concepts
- JobRepository schedule and interact with the job

- Job may have one or more steps. Each steps have Read, Process e Write.

- Processors  ingest data, transform it, and then pipe it out somewhere else. 
Useful to convert the names to uppercase. It receives an Object and returns other or same transformed Object.
With Processor we can transform chunk in/out

- Reades/Writers from/to FlatFile, XML, Database, List, Spring Data, etc 

- By default exceptions thrown in Readers does not cause rollback, only in Writers.

- TaskletStep is a simple job. It does not require Reader/Writer. Useful for simple procedures such as purging files.

- Transaction propagation, isolation and timeout 

- Step Flow makes able to work with conditional executions (if clause)

- Skip and Retry functions with policy definitions
