package uk.co.jsweetsolutions.workflow.tasks.query;

import lombok.Data;

@Data
public class FetchTaskSummariesQuery {
	
	private final int offset;
	private final int limit;
}
