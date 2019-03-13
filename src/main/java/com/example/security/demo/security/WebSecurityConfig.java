package com.example.security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Configuración de las autorizaciones
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout()
			.permitAll();
	}
	
	// Creación de usuarios
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// A través de auth se puede configurar el repositorio en el que se van a
		// guardar los usuarios
		auth
			.inMemoryAuthentication()
			.withUser("user")
				.password("user")
				.roles("USER")
				.and()
			.withUser("admin")
				.password("admin")
				.roles("USER", "ADMIN");
	}

}
