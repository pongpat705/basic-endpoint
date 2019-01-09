package com.basic.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.basic.app.security.CustomAuthenticationProvider;
import com.basic.app.security.JWTAuthenticationFilter;
import com.basic.app.security.JWTLoginFilter;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter{ 
	 
	@Autowired 
	CustomAuthenticationProvider authenProvider;
 
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenProvider);
	} 
 
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable()
			.authorizeRequests() 
			.antMatchers("/login").permitAll()
			.antMatchers("/api/authen").permitAll()
			.antMatchers("/api/**").authenticated() 
			.and()
			.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class) 
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) 
			; 
	} 
 
	@Override 
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	} 
 
} 