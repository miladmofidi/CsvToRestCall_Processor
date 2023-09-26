package com.mofidsoft.csvtorestcall_processor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Milad Mofidi (milad.mofidi@cmas-systems.com)
 * 7/3/2023
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	private String server;
	private String path;
	private String token;
	private String suplistnode;
	private String dirPath;
	private String filename;
	private String children;


}
