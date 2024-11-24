package com.nagarro.bankhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
	@Bean
	DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("net.ucanaccess.jdbc.UcanaccessDriver");

		dataSource.setUrl("jdbc:ucanaccess://C:/Users/rohitsrivastava/Downloads/bankhub/accountsdb.accdb");

		return dataSource;
	}
}