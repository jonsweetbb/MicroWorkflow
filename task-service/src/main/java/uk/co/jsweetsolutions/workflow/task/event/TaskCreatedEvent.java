package uk.co.jsweetsolutions.workflow.task.event;

import java.time.LocalDateTime;

import lombok.Data;

//TODO make super class containing all common Task Event attributes
@Data
public class TaskCreatedEvent {
	private final String id;
	
	private final LocalDateTime createdOn;
}
