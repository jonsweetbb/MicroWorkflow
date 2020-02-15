package uk.co.jsweetsolutions.workflow.task.command;

import java.time.LocalDateTime;
import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CreateTaskCmd {
	@TargetAggregateIdentifier
	private final String id;
	
	private LocalDateTime createdOn;

	private String assigneeGroupId;
	private String ownerGroupId;
	
	private String taskName;

	public CreateTaskCmd(String id, LocalDateTime createdOn, String assigneeGroupId, String ownerGroupId,
			String taskName) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.assigneeGroupId = assigneeGroupId;
		this.ownerGroupId = ownerGroupId;
		this.taskName = taskName;
	}
		
	
}
