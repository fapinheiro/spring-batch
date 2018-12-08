/**
 * @author filipe.pinheiro, 29/09/2018
 */
package br.com.batch.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"br.com.batch"})
//@EnableJpaRepositories(basePackages={"mt.com.vodafone"}) 
//@EntityScan(basePackages={"mt.com.vodafone"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		// See application.properties, web context is unabled.
	}
}
