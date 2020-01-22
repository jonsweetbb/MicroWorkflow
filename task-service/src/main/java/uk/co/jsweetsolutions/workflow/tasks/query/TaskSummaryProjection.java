package uk.co.jsweetsolutions.workflow.tasks.query;

import java.util.List;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.jsweetsolutions.workflow.tasks.domain.TaskState;
import uk.co.jsweetsolutions.workflow.tasks.events.CreateTaskEvent;

@Component
public class TaskSummaryProjection {
	
	@Autowired(required = true)
	private TasksRepository wfTaskRepository;
	
	@EventHandler
	public void on(CreateTaskEvent evt) {
		wfTaskRepository.save(new TaskSummary(evt.getId(), evt.getCreatedOn(), TaskState.ASSIGNED));
	}
	
	@QueryHandler
	public List<TaskSummary> handle(FetchTaskSummariesQuery query){
		//TODO implement pagination
		return wfTaskRepository.findAll();
	}
	
	@QueryHandler
	public Optional<TaskSummary> handle(FecthTaskSummaryByIdQuery query) {
		return wfTaskRepository.findById(query.getId());
	}
}
