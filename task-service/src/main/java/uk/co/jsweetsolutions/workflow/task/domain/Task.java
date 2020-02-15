package uk.co.jsweetsolutions.workflow.task.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateRoot;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupByIdQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.UserSummary;
import uk.co.jsweetsolutions.workflow.task.command.CloseTaskCmd;
import uk.co.jsweetsolutions.workflow.task.command.CreateTaskCmd;
import uk.co.jsweetsolutions.workflow.task.event.TaskClosedEvent;
import uk.co.jsweetsolutions.workflow.task.event.TaskCreatedEvent;

// TODO separate out as POJO and use inheritance for Aggregate, Command
@Aggregate
@Data
public class Task {
	private static final Logger log = LoggerFactory.getLogger(Task.class);
	
	@AggregateIdentifier
	private String id;
	
	private LocalDateTime createdOn;
	private LocalDateTime lastUpdatedOn;
	
	private TaskState state;
	
	private String ownerGroupId;
	private String assigneeGroupId;
	
	private String taskName;
	
	public Task(){}

	@CommandHandler
	public Task(CreateTaskCmd cmd){
		log.debug("handling {}", cmd);
		AggregateLifecycle.apply(new TaskCreatedEvent(cmd.getId()
													, cmd.getCreatedOn()
													, cmd.getAssigneeGroupId()
													, cmd.getOwnerGroupId()
													, cmd.getTaskName()));
	}
	
	@EventSourcingHandler
	public void on(TaskCreatedEvent evt) {
		log.debug("handling {}", evt);
		this.id = evt.getId();
		this.createdOn = evt.getCreatedOn();
		this.lastUpdatedOn = evt.getCreatedOn();
		this.state = TaskState.ASSIGNED;
		this.ownerGroupId = evt.getOwnerGroupId();
		this.assigneeGroupId = evt.getAssigneeGroupId();
		this.taskName = evt.getTaskName();
	}
	
	@CommandHandler
	public Task handle(CloseTaskCmd cmd, QueryGateway queryGateway) {
		switch (this.state) {
		case ASSIGNED:
			AssignmentGroupByIdQuery ownerQuery = new AssignmentGroupByIdQuery();
			ownerQuery.setGroupId(ownerGroupId);
			List<AssignmentGroupByIdQuery> queries = Collections.singletonList(ownerQuery);
			if(this.ownerGroupId != this.assigneeGroupId) {
				AssignmentGroupByIdQuery assigneeQuery = new AssignmentGroupByIdQuery();
				assigneeQuery.setGroupId(assigneeGroupId);
				queries.add(assigneeQuery);
			}
			
			List<AssignmentGroupSummary> assignmentGroups = queries.parallelStream()
					.map(query -> queryGateway.query(query, ResponseTypes.instanceOf(AssignmentGroupSummary.class)).join())
					.collect(Collectors.toList());
			
			boolean userHasPermission = assignmentGroups.stream()
					.flatMap(agSummary -> agSummary.getMembers().stream())
					.anyMatch(member -> member.getId().equals(cmd.getActioningUserId()));
			
			if(!userHasPermission) {
				String message = "User [" + cmd.getActioningUserId() + "] is not in the right groups to close the task [" + this.id + "]: " 
						+ assignmentGroups.stream().map(ag -> ag.getGroupName()).collect(Collectors.joining("] , [", "[", "]"));
				throw new IllegalStateException(message);
			}
			AggregateLifecycle.apply(new TaskClosedEvent(cmd.getId(), cmd.getClosedOn(), cmd.getActioningUserId()));
			break;
		default:
			throw new IllegalStateException("Task [" + this.id + "] cannot be transitioned from " + this.state + " to CLOSED");
		}
		return this;
	}
	
	@EventSourcingHandler
	public void on(TaskClosedEvent evt) {
		this.state = TaskState.CLOSED;
		this.lastUpdatedOn = evt.getClosedOn();
	}
}
