package uk.co.jsweetsolutions.workflow.assignmentgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

@SpringBootApplication
@EnableJpaRepositories
public class AssignmentGroupServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentGroupServiceApplication.class, args);
	}

}
