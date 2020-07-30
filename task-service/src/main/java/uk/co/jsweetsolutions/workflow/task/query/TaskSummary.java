package uk.co.jsweetsolutions.workflow.task.query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class TaskSummary {
	
	@Id
	private String id;
	
	private LocalDateTime createdOn;
	private LocalDateTime lastUpdatedOn;
	
	private TaskState state;
	
	public TaskSummary() {
		super();
	}

	public TaskSummary(String id, LocalDateTime createdOn, LocalDateTime lastUpdatedOn, TaskState state) {
		super();
		this.id = id;
		this.createdOn = createdOn.withNano(0);
		this.lastUpdatedOn = lastUpdatedOn.withNano(0);
		this.state = state;
	}
	
}
