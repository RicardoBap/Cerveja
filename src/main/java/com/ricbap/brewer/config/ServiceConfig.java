package com.ricbap.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ricbap.brewer.service.CadastroCervejaService;
import com.ricbap.brewer.storage.FotoStorage;
import com.ricbap.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses =  CadastroCervejaService.class)
public class ServiceConfig {
	
	@Bean
	public FotoStorage fotoStorage() {
		return new FotoStorageLocal();
	}

}
