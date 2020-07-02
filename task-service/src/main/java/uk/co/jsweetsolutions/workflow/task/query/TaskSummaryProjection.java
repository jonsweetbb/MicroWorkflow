package uk.co.jsweetsolutions.workflow.task.query;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import uk.co.jsweetsolutions.workflow.task.event.TaskClosedEvent;
import uk.co.jsweetsolutions.workflow.task.event.TaskCreatedEvent;
import uk.co.jsweetsolutions.workflow.task.query.FetchTaskSummaryByIdQuery;
import uk.co.jsweetsolutions.workflow.task.query.FetchTaskSummariesQuery;
import uk.co.jsweetsolutions.workflow.task.query.TaskState;
import uk.co.jsweetsolutions.workflow.task.query.TaskSummary;

@Component
public class TaskSummaryProjection {
	
	private Logger log = Logger.getLogger(TaskSummaryProjection.class.getName());
	
	@Autowired(required = true)
	private TasksRepository wfTaskRepository;
	
	@Autowired(required = true)
	private QueryUpdateEmitter queryUpdateEmitter;
	
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
			queryUpdateEmitter.emit(FetchTaskSummariesByIdsQuery.class
					, query -> query.getIds().contains(evt.getId())
					, t);
		});
		
	}
	
	@QueryHandler
	public List<TaskSummary> handle(FetchTaskSummariesQuery query){
		log.log(Level.FINE, "Fecthing all tasks");
		// TODO implement paging
		Pageable pageable =
				PageRequest.of(0, 10, Sort.by("createdOn").descending());
		return wfTaskRepository.findAll(pageable).toList();
	}
	
	@QueryHandler
	public Optional<TaskSummary> handle(FetchTaskSummaryByIdQuery query) {
		return wfTaskRepository.findById(query.getId());
	}
	
	@QueryHandler
	public List<TaskSummary> handle(FetchTaskSummariesByIdsQuery query){
		return wfTaskRepository.findAllById(query.getIds());
	}
}
