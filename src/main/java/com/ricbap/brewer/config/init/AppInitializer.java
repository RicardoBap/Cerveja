package com.ricbap.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ricbap.brewer.config.JPAConfig;
import com.ricbap.brewer.config.MailConfig;
import com.ricbap.brewer.config.S3Config;
import com.ricbap.brewer.config.SecurityConfig;
import com.ricbap.brewer.config.ServiceConfig;
import com.ricbap.brewer.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class, SecurityConfig.class, S3Config.class  }; //S3Config.class 
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {		
		return new Class<?>[] { WebConfig.class, MailConfig.class };
	}

	@Override
	protected String[] getServletMappings() {		
		return new String[] { "/" };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	@Override
	protected Filter[] getServletFilters() {
		// Após a inserção do csrf parou de funcionar 
		//CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		//characterEncodingFilter.setEncoding("UTF-8");
		//characterEncodingFilter.setForceEncoding(true);
		
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
		return new Filter[] { httpPutFormContentFilter }; // characterEncodingFilter
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setInitParameter("spring.profiles.default", "local");
	}

}
