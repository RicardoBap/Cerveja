package com.ricbap.brewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
//@PropertySource(value = { "file://${LENOVO}/.brewer-s3.properties" }, ignoreResourceNotFound = true) 
@PropertySource( value = { "classpath:env/nuvem.properties" }, ignoreResourceNotFound = true )
public class S3Config {
	
	@Autowired
	private Environment env;
	
	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials credenciais = new BasicAWSCredentials(
				env.getProperty(), env.getProperty());
						// KEY                  SECRET
		System.out.println(">>> access_key: " + env.getProperty());
		System.out.println(">>> secret_access: " + env.getProperty();
		
		AmazonS3 amazonS3 = new AmazonS3Client(credenciais, new ClientConfiguration());
		Region regiao = Region.getRegion(Regions.US_EAST_1);
		amazonS3.setRegion(regiao);
		return amazonS3;
	}

} 
