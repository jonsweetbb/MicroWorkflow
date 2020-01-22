package uk.co.jsweetsolutions.workflow.tasks.command;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;
import uk.co.jsweetsolutions.workflow.tasks.domain.TaskState;

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
