package uk.co.jsweetsolutions.workflow.task.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("uk.co.jsweetsolutions.workflow.task.query")
public class DbConfig {
}
