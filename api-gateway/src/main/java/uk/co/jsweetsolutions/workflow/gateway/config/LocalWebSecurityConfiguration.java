package uk.co.jsweetsolutions.workflow.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@Profile(value = "local")
public class LocalWebSecurityConfiguration extends BaseWebSecurityConfiguration{
	
	@Override
 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth.inMemoryAuthentication().withUser("1").password("{noop}password").roles("USER");
 	}
	
	
}
