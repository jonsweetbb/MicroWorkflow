package uk.co.jsweetsolutions.workflow.task.command;

import java.time.LocalDateTime;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CreateTaskCmd {
	@TargetAggregateIdentifier
	private final String id;
	
	private LocalDateTime createdOn;

	public CreateTaskCmd(String id, LocalDateTime createdOn) {
		this.id = id;
		this.createdOn = createdOn;
	}
	
	
}
