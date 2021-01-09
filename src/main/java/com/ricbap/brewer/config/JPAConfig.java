package com.ricbap.brewer.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.repository.CervejaRepository;

@Configuration
@ComponentScan(basePackageClasses = CervejaRepository.class)
@EnableJpaRepositories(basePackageClasses = CervejaRepository.class, enableDefaultTransactions = false)
@EnableTransactionManagement
public class JPAConfig {
	
	/* @Profile("local")
	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true);
		return dataSourceLookup.getDataSource("jdbc/brewerDB");
	} */
	
	
	@Profile("prod")
	@Bean
	public DataSource dataSourceProd() throws URISyntaxException {
		URI jdbUri = new URI(System.getenv("JAWSDB_URL"));

	    String username = jdbUri.getUserInfo().split(":")[0];
	    String password = jdbUri.getUserInfo().split(":")[1];
	    String port = String.valueOf(jdbUri.getPort());
	    String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
	    
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setUrl(jdbUrl);
	    dataSource.setUsername(username);
	    dataSource.setPassword(password);
	    dataSource.setInitialSize(10);

	    return dataSource;
	} 
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(false);
		adapter.setGenerateDdl(false);
		//adapter.setDatabasePlatform("org.hibernate.dialect.Dialect"); //<<<<<<<<<< NAO NECESSARIO ACARRETA PROBLEMA
		return adapter;                                                    
	}	
	
	@Bean public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {		  
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan(Cerveja.class.getPackage().getName());
		factory.setMappingResources("sql/consultas-nativas.xml");
		factory.afterPropertiesSet(); 
		return factory.getObject(); 
	}	
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}


/*@Bean
public EntityManagerFactory entityManagerFactory() throws SQLException {
	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	vendorAdapter.setGenerateDdl(true)

	LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	factory.setJpaVendorAdapter(vendorAdapter);
	factory.setPackagesToScan("com.ricbap.brewer.model");
	factory.setDataSource(dataSource());
	factory.afterPropertiesSet();
	return factory.getObject();
}*/
