package uk.co.jsweetsolutions.workflow.task.event;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskClosedEvent {
	private final String id;
	
	private final LocalDateTime closedOn;
}
