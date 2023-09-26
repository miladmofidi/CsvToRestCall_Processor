package com.mofidsoft.csvtorestcall_processor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Milad Mofidi (milad.mofidi@cmas-systems.com)
 * 7/3/2023
 */

@Data
public class Properties {

	@JsonProperty( "nowo:dl005_fornecedoresClientes_empresaFornecedorCliente" )
	private String nowoDl005FornecedoresClientesEmpresaFornecedorCliente;

	@JsonProperty( "nowo:dl005_fornecedoresClientes_grupoEconomico" )
	private String nowoDl005FornecedoresClientesGrupoEconomico;

	@JsonProperty( "nowo:dl005_fornecedoresClientes_morada" )
	private String nowoDl005FornecedoresClientesMorada;

	@JsonProperty( "nowo:dl005_fornecedoresClientes_NIPC" )
	private String nowoDl005FornecedoresClientesNIPC;

	@JsonProperty( "nowo:dl005_fornecedoresClientes_tipoCliente" )
	private String nowoDl005FornecedoresClientesTipoCliente;



	public static Properties createProperties( String empresa, String grupo, String morada, String nipc, String tipo ) {
		Properties properties = new Properties();
		properties.setNowoDl005FornecedoresClientesEmpresaFornecedorCliente( empresa );
		properties.setNowoDl005FornecedoresClientesGrupoEconomico( grupo );
		properties.setNowoDl005FornecedoresClientesMorada( morada );
		properties.setNowoDl005FornecedoresClientesNIPC( nipc );
		properties.setNowoDl005FornecedoresClientesTipoCliente( tipo );
		return properties;
	}
}
