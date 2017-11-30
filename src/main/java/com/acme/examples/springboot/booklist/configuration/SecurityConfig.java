package com.acme.examples.springboot.booklist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.acme.examples.springboot.booklist.repository.ReaderRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ReaderRepository readerRepository;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/booklist/**").access("hasRole('READER')")
			.antMatchers("/home/**").access("hasRole('READER')")
			.antMatchers("/**").permitAll()
			.and().formLogin().loginPage("/login").failureUrl("/login?error=true");
		
		// CSRF 
		// https://stackoverflow.com/questions/21128058/invalid-csrf-token-null-was-found-on-the-request-parameter-csrf-or-header
		// http.csrf().disable();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
				final UserDetails user = readerRepository.findOne(username);
				if (user != null) {
					return user;
				} else {
					throw new UsernameNotFoundException("User '" + username + "' not found.");
				}
			}
		});
	}
}