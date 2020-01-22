package uk.co.jsweetsolutions.workflow.tasks.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

public interface TasksRepository extends CrudRepository<TaskSummary, String> {
	Optional<TaskSummary> findById(String id);
	
	List<TaskSummary> findAll();
}
