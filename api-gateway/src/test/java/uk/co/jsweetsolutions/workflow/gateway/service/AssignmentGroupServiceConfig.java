package uk.co.jsweetsolutions.workflow.gateway.service;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import uk.co.jsweetsolutions.workflow.assignmentgroup.command.CreateGroupCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupByIdQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummariesQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.UserSummary;

@Configuration
public class AssignmentGroupServiceConfig {

	@Bean
	public QueryGateway getQueryGateway() {
		QueryGateway gateway = mock(QueryGateway.class);

		AssignmentGroupSummary assigmenGroupSummary = new AssignmentGroupSummary("1", "one", Collections.singletonList(new UserSummary("1", "Bob", "Smith")));
		
		CompletableFuture<AssignmentGroupSummary> queryOneResult = new CompletableFuture<AssignmentGroupSummary>();
		queryOneResult.complete(assigmenGroupSummary);

		CompletableFuture<List<AssignmentGroupSummary>> allResults = new CompletableFuture<>();
		allResults.complete(Collections.singletonList(assigmenGroupSummary));
		
		when(gateway.query(any(AssignmentGroupSummariesQuery.class), any(ResponseType.class)))
			.thenReturn(allResults);
		
		when(gateway.query(any(AssignmentGroupByIdQuery.class), any(ResponseType.class)))
			.thenReturn(queryOneResult);
		
		return gateway;
	}
	
	@Bean
	public CommandGateway getCommandGateway() {
		CommandGateway commandGateway = mock(CommandGateway.class);
		
		when(commandGateway.sendAndWait(any(CreateGroupCmd.class)))
				.thenReturn("OK");
		
		return commandGateway;
	}

}
