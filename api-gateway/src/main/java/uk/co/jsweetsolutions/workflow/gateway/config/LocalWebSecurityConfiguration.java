package uk.co.jsweetsolutions.workflow.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@Profile(value = "local")
public class LocalWebSecurityConfiguration extends BaseWebSecurityConfiguration{
	

	
	@Bean
	public MapReactiveUserDetailsService userDetailsService(){
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("1")
				.password("password")
				.roles("USER")
				.build();
		return new MapReactiveUserDetailsService(userDetails);
	}
}
