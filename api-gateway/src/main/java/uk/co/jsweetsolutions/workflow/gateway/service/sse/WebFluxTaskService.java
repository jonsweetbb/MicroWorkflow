package uk.co.jsweetsolutions.workflow.gateway.service.sse;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import uk.co.jsweetsolutions.workflow.gateway.service.SessionConstants;
import uk.co.jsweetsolutions.workflow.task.query.FetchTaskSummariesByIdsQuery;
import uk.co.jsweetsolutions.workflow.task.query.TaskSummary;

import java.util.List;

@RestController
@RequestMapping("/stream")
public class WebFluxTaskService {

	@Autowired
	private QueryGateway queryGateway;


	@GetMapping(path = "/tasks/updates"
//			, produces = MediaType.APPLICATION_STREAM_JSON_VALUE
	)
	public Flux<TaskSummary> streamTasksUpdates(WebSession webSession){
		FetchTaskSummariesByIdsQuery query = new FetchTaskSummariesByIdsQuery();
		if(webSession.getAttribute(SessionConstants.LIVE_TASK_IDS) == null)
			throw new IllegalStateException("Session value not set");
		query.setIds(webSession.getAttribute(SessionConstants.LIVE_TASK_IDS));
		SubscriptionQueryResult<List<TaskSummary>, TaskSummary> queryResult =
				queryGateway.subscriptionQuery(query
				, ResponseTypes.multipleInstancesOf(TaskSummary.class)
				, ResponseTypes.instanceOf(TaskSummary.class));
		return queryResult.updates()
				.doOnEach(System.out::println)
				.doFinally(it -> queryResult.close())
				;
	}

}
