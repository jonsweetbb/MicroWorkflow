package uk.co.jsweetsolutions.workflow.task.command;

import java.time.LocalDateTime;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CloseTaskCmd {
	@TargetAggregateIdentifier
	private final String id;
	
	private final LocalDateTime closedOn;
}
