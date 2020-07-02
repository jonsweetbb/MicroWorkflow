package uk.co.jsweetsolutions.workflow.task.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import uk.co.jsweetsolutions.workflow.task.query.TaskSummary;

public interface TasksRepository extends PagingAndSortingRepository<TaskSummary, String> {
	Optional<TaskSummary> findById(String id);
	
	List<TaskSummary> findAllById(Iterable<String> ids);
}
