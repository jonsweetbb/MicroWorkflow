package uk.co.jsweetsolutions.workflow.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public abstract class BaseWebSecurityConfiguration
{

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity){
		log.info("Configuring Base Security");
		httpSecurity.authorizeExchange(exchanges -> exchanges
			.anyExchange().authenticated()
		)
				.httpBasic(Customizer.withDefaults())
				.csrf().disable();
		return httpSecurity.build();
	}
}
