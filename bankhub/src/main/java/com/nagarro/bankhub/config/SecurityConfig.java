package com.nagarro.bankhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Value("${adminname}")
	private String adminName;

	@Value("${adminpassword}")
	private String adminPassword;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService() {
		PasswordEncoder encoder = passwordEncoder();

		// Create admin user
		UserDetails adminUser = User.withUsername(adminName).password(encoder.encode(adminPassword)).roles("ADMIN")
				.build();

		// Create normal user
		UserDetails normalUser = User.withUsername("user").password(encoder.encode("user")).roles("USER").build();

		return new InMemoryUserDetailsManager(adminUser, normalUser);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Enable CSRF protection unless you have stateless APIs
				.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.maximumSessions(1).maxSessionsPreventsLogin(true));

		return http.build();
	}

	@Bean
	HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

}
