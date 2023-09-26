package com.mofidsoft.csvtorestcall_processor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Milad Mofidi (milad.mofidi@cmas-systems.com)
 * 7/3/2023
 */
@Data
@Builder
public class RestRequestBody {
	public static final String NODE_TYPE = "nowo:dl005_fornecedoresClientes";
	public static final String ASPECTNAME = "cm:auditable";
	@JsonProperty("aspectNames")
	private List<String> aspectNames;
	@JsonProperty("isFolder")
	private Boolean isFolder;
	@JsonProperty("isFile")
	private Boolean isFile;
	@JsonProperty("name")
	private String name;
	@JsonProperty("nodeType")
	private String nodeType;
	@JsonProperty("properties")
	private Properties properties;
	@JsonProperty("parentId")
	private String parentId;




}
