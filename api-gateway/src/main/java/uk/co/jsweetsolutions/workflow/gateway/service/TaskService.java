package uk.co.jsweetsolutions.workflow.gateway.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.co.jsweetsolutions.workflow.tasks.command.CreateTaskCmd;
import uk.co.jsweetsolutions.workflow.tasks.domain.Task;
import uk.co.jsweetsolutions.workflow.tasks.domain.TaskState;
import uk.co.jsweetsolutions.workflow.tasks.query.FetchTaskSummariesQuery;
import uk.co.jsweetsolutions.workflow.tasks.query.TaskSummary;

@RestController
public class TaskService {

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private QueryGateway queryGateway;
	
	@PostMapping(path = "/task", produces = "application/json")
	public TaskSummary createTask() {
		CreateTaskCmd cmd = new CreateTaskCmd(UUID.randomUUID().toString(), LocalDateTime.now());
		String result = commandGateway.sendAndWait(cmd, 10000, TimeUnit.MILLISECONDS);
		//TODO throw exception if null
		return result == null ? null : findTaskById(cmd.getId());
	}
	
	@GetMapping(path = "/task", produces = "application/json")
	public List<TaskSummary> findAllTasks(){
		//TODO implement pagination
		return queryGateway.query(new FetchTaskSummariesQuery(0, 10), ResponseTypes.multipleInstancesOf(TaskSummary.class)).join();
	}
	
	@GetMapping(path = "/task/{id}")
	public TaskSummary findTaskById(@PathVariable(name = "id") String id) {
		
		return null;
	}
}
