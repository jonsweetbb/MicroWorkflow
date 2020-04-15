package uk.co.jsweetsolutions.workflow.tasks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupByIdQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.UserSummary;
import uk.co.jsweetsolutions.workflow.task.command.CloseTaskCmd;
import uk.co.jsweetsolutions.workflow.task.command.CreateTaskCmd;
import uk.co.jsweetsolutions.workflow.task.domain.Task;
import uk.co.jsweetsolutions.workflow.task.domain.TaskState;
import uk.co.jsweetsolutions.workflow.task.event.TaskClosedEvent;
import uk.co.jsweetsolutions.workflow.task.event.TaskCreatedEvent;

public class CommandTests {

	private FixtureConfiguration<Task> fixture;
	
	private static TaskCreatedEvent TASK_CREATED_EVT;
	
	private static LocalDateTime createdOn = LocalDateTime.now();
	
	private static QueryGateway queryGateway;
	
	@BeforeAll
	public static void globalSetUp() {
		
		
		createdOn = LocalDateTime.now();
		TaskCreatedEvent.Builder taskCreatedBuilder = new TaskCreatedEvent.Builder("123");
		TASK_CREATED_EVT =  taskCreatedBuilder.createdOn(createdOn)
			.withAssigneeGroup("1")
			.withName("Test Task")
			.withOwnerGroup("2")
			.build();
	}
	
	@BeforeEach
	public  void setUp() {
		AssignmentGroupByIdQuery queryOne = new AssignmentGroupByIdQuery();
		queryOne.setGroupId("1");
		AssignmentGroupByIdQuery queryTwo = new AssignmentGroupByIdQuery();
		queryTwo.setGroupId("2");
		
		CompletableFuture<AssignmentGroupSummary> queryOneResult = new CompletableFuture<AssignmentGroupSummary>();
		CompletableFuture<AssignmentGroupSummary> queryTwoResult = new CompletableFuture<AssignmentGroupSummary>();
		queryOneResult.complete(new AssignmentGroupSummary("1", "one", Collections.singletonList(new UserSummary("1", "Bob", "Smith"))));
		queryTwoResult.complete(new AssignmentGroupSummary("2", "two", Collections.singletonList(new UserSummary("2", "Fred", "Jones"))));
		
		queryGateway = mock(QueryGateway.class);
		when(queryGateway.query(any(AssignmentGroupByIdQuery.class), any(ResponseType.class)))
				.thenReturn(queryOneResult);
		
		fixture = new AggregateTestFixture<>(Task.class);
		fixture.registerInjectableResource(queryGateway);
	}
	
	@Test
	public void testCreateTaskCommand() {
		
		
		fixture.givenNoPriorActivity()
			.when(new CreateTaskCmd("123", createdOn, "1", "2", "Test Task"))
			.expectSuccessfulHandlerExecution()
			.expectEvents(TASK_CREATED_EVT)
			.expectState(state -> state.getState().equals(TaskState.ASSIGNED));
	}
	
	@Test
	public void testCloseTaskCommand() {
		LocalDateTime closedOn = LocalDateTime.now();
		
		fixture.given(TASK_CREATED_EVT)
			.when(new CloseTaskCmd("123", closedOn, "1"))
			.expectSuccessfulHandlerExecution()
			.expectEvents(new TaskClosedEvent("123", closedOn, "1"));
	}
	
	@Test
	public void testCloseTaskInvalidState() {
		fixture.given(TASK_CREATED_EVT, new TaskClosedEvent("123", LocalDateTime.now(), "1"))
			.when(new CloseTaskCmd("123", LocalDateTime.now(), "1"))
			.expectException(IllegalStateException.class);
	}
}
