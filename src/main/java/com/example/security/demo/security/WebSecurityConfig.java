package com.example.security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * Clase que añade la configuracion de Spring Security.
 * Para añadir la configuración se puede crear un bean específico que implemente WebSecurityConfigurer o
 * ampliar WebSecurityConfigurerAdapter, como en este caso.
 */
@Configuration
@EnableWebSecurity // Habilitar seguridad web
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 *  Configuración de las autorizaciones.
	 *  Este método se reemplaza para configurar la protección de las solicitudes por parte 
	 *  de los interceptores.
	 */
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
	
	
	/*
	 * Creación de usuarios.
	 * Reemplaza la configuración de servicios de detalles de usuarios.
	 * Métodos disponibles para UserDetailsManagerConfigurer.UserDetailsBuilder:
	 * - accountExpired(boolean)
	 * - accountLocked
	 * - authorities
	 * - credentialsExpired
	 * - disabled
	 * 
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		// A través de auth se puede configurar el repositorio en el que se van a
		// guardar los usuarios
		auth
			.inMemoryAuthentication()	// Permite habilitar, configurar y completar un repositorio de usuarios en memoria
			.withUser("user")	// Permite añadir usuarios. Devuelve UserDetailsManagerConfigurer.UserDetailsBuilder (ver métodos disponibles)
				.password("{noop}user")
				.roles("USER")
				.and()
			.withUser("admin")
				.password("{noop}admin")
				.roles("USER", "ADMIN");
	}
	
	/*
	public UserDetailsService userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserDetails user = User.withUsername("user")
				.password("password")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(user);
	}
	*/

}
