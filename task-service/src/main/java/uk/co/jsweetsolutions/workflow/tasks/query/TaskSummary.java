package uk.co.jsweetsolutions.workflow.tasks.query;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import uk.co.jsweetsolutions.workflow.tasks.domain.TaskState;

@Data
@Entity
public class TaskSummary {
	
	@Id
	private String id;
	
	private LocalDateTime createdOn;
	
	private TaskState state;
	
	public TaskSummary() {
		super();
	}
	
	public TaskSummary(String id, LocalDateTime createdOn, TaskState state) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.state = state;
	}

}
