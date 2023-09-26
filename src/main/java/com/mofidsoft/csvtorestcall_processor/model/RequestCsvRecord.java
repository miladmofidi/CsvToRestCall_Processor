package com.mofidsoft.csvtorestcall_processor.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Milad Mofidi (milad.mofidi@cmas-systems.com)
 * 7/1/2023
 */

@Data
@Builder
public class RequestCsvRecord {

	private String grupo;

	private String empresa;

	private String numeroContribuinte;

	private String tipo;

	private String morada;

}

