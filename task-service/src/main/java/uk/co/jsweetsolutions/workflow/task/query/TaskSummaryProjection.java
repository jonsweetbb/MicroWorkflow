package uk.co.jsweetsolutions.workflow.task.query;

import java.util.List;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.jsweetsolutions.workflow.task.event.TaskClosedEvent;
import uk.co.jsweetsolutions.workflow.task.event.TaskCreatedEvent;
import uk.co.jsweetsolutions.workflow.task.query.FetchTaskSummaryByIdQuery;
import uk.co.jsweetsolutions.workflow.task.query.FetchTaskSummariesQuery;
import uk.co.jsweetsolutions.workflow.task.query.TaskState;
import uk.co.jsweetsolutions.workflow.task.query.TaskSummary;

@Component
public class TaskSummaryProjection {
	
	@Autowired(required = true)
	private TasksRepository wfTaskRepository;
	
	@EventHandler
	public void on(TaskCreatedEvent evt) {
		// TODO create builder for TaskSummary
		wfTaskRepository.save(new TaskSummary(evt.getId(), evt.getCreatedOn(), evt.getCreatedOn(), TaskState.ASSIGNED));
	}
	
	@EventHandler
	public void on(TaskClosedEvent evt) {
		Optional<TaskSummary> task = wfTaskRepository.findById(evt.getId());
		task.ifPresent(t -> {
			t.setState(TaskState.CLOSED);
			t.setLastUpdatedOn(evt.getClosedOn());
			wfTaskRepository.save(t);
		});
	}
	
	@QueryHandler
	public List<TaskSummary> handle(FetchTaskSummariesQuery query){
		//TODO implement pagination
		return wfTaskRepository.findAll();
	}
	
	@QueryHandler
	public Optional<TaskSummary> handle(FetchTaskSummaryByIdQuery query) {
		return wfTaskRepository.findById(query.getId());
	}
}
