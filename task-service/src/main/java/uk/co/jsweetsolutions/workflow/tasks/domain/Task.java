package uk.co.jsweetsolutions.workflow.tasks.domain;

import java.time.LocalDateTime;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import lombok.Data;
import uk.co.jsweetsolutions.workflow.tasks.command.CreateTaskCmd;
import uk.co.jsweetsolutions.workflow.tasks.events.CreateTaskEvent;

// TODO separate out as POJO and use inheritance for Aggregate, Command
@Aggregate
@Data
public class Task {
	private static final Logger log = LoggerFactory.getLogger(Task.class);
	
	@AggregateIdentifier
	private String id;
	
	private LocalDateTime createdOn;
	
	private TaskState state;
	
	public Task(){}
	
	public Task(String id, LocalDateTime createdOn, TaskState state) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.state = state;
	}

	@CommandHandler
	public Task(CreateTaskCmd cmd){
		log.debug("handling {}", cmd);
		AggregateLifecycle.apply(new CreateTaskEvent(cmd.getId(), cmd.getCreatedOn()));
	}
	
	@EventSourcingHandler
	public void on(CreateTaskEvent evt) {
		log.debug("handling {}", evt);
		this.id = evt.getId();
		this.createdOn = evt.getCreatedOn();
		this.state = TaskState.ASSIGNED;
	}
}
