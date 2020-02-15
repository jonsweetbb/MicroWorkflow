package uk.co.jsweetsolutions.workflow.task.event;

import java.time.LocalDateTime;

import lombok.Data;

//TODO make super class containing all common Task Event attributes
@Data
public class TaskCreatedEvent {
	private final String id;
	
	private final LocalDateTime createdOn;
	
	private String assigneeGroupId;
	private String ownerGroupId;
	
	private String taskName;

	public TaskCreatedEvent(String id, LocalDateTime createdOn, String assigneeGroupId, String ownerGroupId,
			String taskName) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.assigneeGroupId = assigneeGroupId;
		this.ownerGroupId = ownerGroupId;
		this.taskName = taskName;
	}
	
}
