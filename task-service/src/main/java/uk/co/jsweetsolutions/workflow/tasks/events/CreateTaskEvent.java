package uk.co.jsweetsolutions.workflow.tasks.events;

import java.time.LocalDateTime;

import lombok.Data;

//TODO make super class containing all common Task Event attributes
@Data
public class CreateTaskEvent {
	private final String id;
	
	private final LocalDateTime createdOn;
}
