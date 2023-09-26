package com.mofidsoft.csvtorestcall_processor.service;

import com.mofidsoft.csvtorestcall_processor.config.AppProperties;
import com.mofidsoft.csvtorestcall_processor.model.CSVRecordIndex;
import com.mofidsoft.csvtorestcall_processor.model.Properties;
import com.mofidsoft.csvtorestcall_processor.model.RequestCsvRecord;
import com.mofidsoft.csvtorestcall_processor.model.RestRequestBody;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.mofidsoft.csvtorestcall_processor.model.Properties.createProperties;
import static com.mofidsoft.csvtorestcall_processor.model.RestRequestBody.NODE_TYPE;

@Component
public class CSVProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger( CSVProcessor.class );

	final AppProperties appProperties;

	@Autowired
	public CSVProcessor( AppProperties appProperties ) {
		this.appProperties = appProperties;
	}

	public void csvProcessor() {
		try {
			if ( appProperties != null && ( appProperties.getDirPath() != null && appProperties.getFilename() != null ) ) {
				String csvFilePath = appProperties.getDirPath() + File.separator + appProperties.getFilename();
				File csvFile = new File( csvFilePath );
				if ( csvFile.exists() ) {
					List<String[]> fileLines = csvParser( csvFilePath );
					if ( !fileLines.isEmpty() ) {
						List<RequestCsvRecord> requestCsvRecords = populateRequests( fileLines );
						restCall( requestCsvRecords );
					}
				}
				else {
					LOGGER.error( "CSV file does not exist at path: " + csvFilePath );
				}
			}

		}
		catch ( Exception e ) {
			LOGGER.error( e.getMessage() );
		}

	}

	private List<String[]> csvParser( String csvFile ) {
		String[] line;
		List<String[]> lines = new ArrayList<>();
		try ( CSVReader reader = new CSVReaderBuilder( new FileReader( csvFile ) )
			.withCSVParser( new CSVParserBuilder().withSeparator( ';' ).build() ).withSkipLines( 1 )
			.build() ) {
			while ( ( line = reader.readNext() ) != null ) {
				lines.add( line );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return lines;
	}

	public List<RequestCsvRecord> populateRequests( List<String[]> lines ) {
		List<RequestCsvRecord> requests = new ArrayList<>();
		for ( String[] line : lines ) {
			RequestCsvRecord request = RequestCsvRecord.builder()
				.grupo( line[CSVRecordIndex.GROUP.getIndex()] )
				.empresa( line[CSVRecordIndex.EMPRESA.getIndex()] )
				.numeroContribuinte( line[CSVRecordIndex.NUMERO_CONTRIBUINTE.getIndex()] )
				.tipo( line[CSVRecordIndex.TIPO.getIndex()] )
				.morada( line[CSVRecordIndex.MORADA.getIndex()] ).build();

			requests.add( request );
		}
		return requests;
	}

	public void restCall( List<RequestCsvRecord> requests ) {
		String url = makeUrl();
		String token = appProperties.getToken();

		for ( RequestCsvRecord requestCsvRecord : requests ) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.add( "Authorization", "Basic " + token );

				RestRequestBody restRequestBody = enrichRequestBody( requestCsvRecord );

				HttpEntity<RestRequestBody> request = new HttpEntity<>( restRequestBody, headers );

				ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
					url, HttpMethod.POST, request, new ParameterizedTypeReference<Map<String, Object>>() {

					} );

				if ( response.getStatusCode() != HttpStatus.CREATED ) {
					LOGGER.error( "An unexpected status code happened in calling the endpoint for NIPC: {}, HTTP status code that returned is: {} and response body detail is: {} ",
						requestCsvRecord.getNumeroContribuinte(), response.getStatusCode(), response.getBody() );
				}
				else {
					LOGGER.info( "Successful endpoint call happened for NIPC: {}", requestCsvRecord.getNumeroContribuinte() );
				}
			}
			catch ( Exception e ) {
				LOGGER.error( e.getMessage() );
			}

		}

	}

	private String makeUrl() {

		return appProperties.getServer()
			+ appProperties.getPath()
			+ appProperties.getSuplistnode()
			+ "/"
			+ appProperties.getChildren()
			+ "?autoRename=true";

	}

	private RestRequestBody enrichRequestBody( RequestCsvRecord record ) {

		Properties properties = createProperties( record.getEmpresa(), record.getGrupo(), record.getMorada(), record.getNumeroContribuinte(), record.getTipo() );
		return RestRequestBody.builder()
			.aspectNames( new ArrayList<>( List.of( RestRequestBody.ASPECTNAME ) ) )
			.isFolder( false )
			.isFile( true )
			.name( UUID.randomUUID().toString() )
			.nodeType( NODE_TYPE )
			.properties( properties )
			.parentId( appProperties.getSuplistnode() )
			.build();

	}

}
