package uk.co.jsweetsolutions.workflow.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public abstract class BaseWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/error").permitAll()
			.antMatchers("/**")
				.hasRole("USER")
				.and().httpBasic()
				.and().csrf().disable()
			;
	}
}
