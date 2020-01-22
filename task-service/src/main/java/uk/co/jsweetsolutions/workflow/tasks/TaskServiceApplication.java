package uk.co.jsweetsolutions.workflow.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import uk.co.jsweetsolutions.workflow.tasks.query.TasksRepository;

@SpringBootApplication
@EnableJpaRepositories("uk.co.jsweetsolutions.workflow.tasks.query")
public class TaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskServiceApplication.class, args);
	}

}
