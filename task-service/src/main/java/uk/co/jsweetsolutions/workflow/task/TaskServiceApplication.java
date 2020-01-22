package uk.co.jsweetsolutions.workflow.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import uk.co.jsweetsolutions.workflow.task.query.TasksRepository;

@SpringBootApplication
@EnableJpaRepositories("uk.co.jsweetsolutions.workflow.task.query")
public class TaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskServiceApplication.class, args);
	}

}
