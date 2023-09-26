package com.mofidsoft.csvtorestcall_processor;

import com.mofidsoft.csvtorestcall_processor.config.AppProperties;
import com.mofidsoft.csvtorestcall_processor.service.CSVProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ${USER} (milad.mofidi@cmas-systems.com)
 * ${DATE}
 */
@SpringBootApplication
@EnableConfigurationProperties( AppProperties.class )
@PropertySource( "classpath:application.yml" )
@Component
public class Main implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger( Main.class );

	private final CSVProcessor csvProcessor;

	public Main( CSVProcessor csvProcessor ) {
		this.csvProcessor = csvProcessor;
	}

	public static void main( String[] args ) {
		SpringApplication.run( Main.class, args );
	}

	@Override
	public void run( String... args ) throws Exception {
		try {
			csvProcessor.csvProcessor();
		}
		catch ( Exception e ) {
			LOGGER.error( "An error happened: {}",e.getMessage() );
		}

	}
}