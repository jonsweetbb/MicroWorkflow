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
	
	public static class Builder {
		private final String id;
		
		private LocalDateTime createdOn;
		
		private String assigneeGroupId;
		private String ownerGroupId;
		
		private String taskName;
		
		public Builder(String id) {
			this.id = id;
		}
		
		public Builder createdOn(LocalDateTime createdOn) {
			this.createdOn = createdOn;
			
			return this;
		}
		
		public Builder withAssigneeGroup(String assigneeGroupId) {
			this.assigneeGroupId = assigneeGroupId;
			
			return this;
		}
		
		public Builder withOwnerGroup(String ownerGroupId) {
			this.ownerGroupId = ownerGroupId;
			
			return this;
		}
		
		public Builder withName(String taskName) {
			this.taskName = taskName;
			
			return this;
		}
		
		public TaskCreatedEvent build() {
			TaskCreatedEvent evt = new TaskCreatedEvent(id, createdOn, assigneeGroupId, ownerGroupId, taskName);
			return evt;
		}
	}
	
}
