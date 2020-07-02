package uk.co.jsweetsolutions.workflow.task.query;

import java.util.List;

import lombok.Data;

@Data
public class FetchTaskSummariesByIdsQuery {
	List<String> ids;
}
