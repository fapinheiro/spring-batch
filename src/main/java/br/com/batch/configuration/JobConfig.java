package br.com.batch.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "job.batch") // Mapeia configuracoes no application.properties
public class JobConfig {

    // Variaveis que vem do arquivo de configuracao appplication.properties
	private Map<String, Integer> threads;
	private Map<String, Integer> limits;


	public Map<String,Integer> getThreads() {
		return this.threads;
	}

	public void setThreads(Map<String,Integer> threads) {
		this.threads = threads;
	}
   

	public Map<String,Integer> getLimits() {
		return this.limits;
	}

	public void setLimits(Map<String,Integer> limits) {
		this.limits = limits;
	}


	@Override
	public String toString() {
		return "{" +
			" threads='" + getThreads() + "'" +
			", limits='" + getLimits() + "'" +
			"}";
	}
	
	
    
}
