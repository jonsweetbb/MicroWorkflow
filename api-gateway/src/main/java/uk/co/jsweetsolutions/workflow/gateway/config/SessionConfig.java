package uk.co.jsweetsolutions.workflow.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringWebSession
@EnableWebFlux
public class SessionConfig {

    @Bean
    public ReactiveSessionRepository sessionRepository(){
        return new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
    }

}
