package uk.co.jsweetsolutions.workflow.assignmentgroup;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import uk.co.jsweetsolutions.workflow.assignmentgroup.command.AddMembersCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.command.CreateGroupCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.domain.AssignmentGroup;
import uk.co.jsweetsolutions.workflow.assignmentgroup.event.GroupCreatedEvt;
import uk.co.jsweetsolutions.workflow.assignmentgroup.event.MemberAddedEvt;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupByNameQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummaryProjection;

@SpringBootTest(classes = AssignmentGroupServiceApplication.class)
public class CommandTests {
	private FixtureConfiguration<AssignmentGroup> fixture;
	
	@BeforeEach
	public void beforeEach() {
		fixture = new AggregateTestFixture<>(AssignmentGroup.class);
	}
	
	@Test
	public void testCreateGroup() {
		AssignmentGroupSummaryProjection projection = mock(AssignmentGroupSummaryProjection.class);
		when(projection.handle(any(AssignmentGroupByNameQuery.class)))
			.thenReturn(Optional.empty());
		fixture.registerInjectableResource(projection);
		
		fixture.givenNoPriorActivity()
			.when(new CreateGroupCmd("123", "TestGroup"))
			.expectSuccessfulHandlerExecution()
			.expectEvents(new GroupCreatedEvt("123", "TestGroup"));
	}

	@Test
	public void testCreateGroupDuplicateName() {
		AssignmentGroupSummaryProjection projection = mock(AssignmentGroupSummaryProjection.class);
		when(projection.handle(any(AssignmentGroupByNameQuery.class)))
			.thenReturn(Optional.of(new AssignmentGroupSummary("321", "ExistingGroup", new ArrayList<>())));
		fixture.registerInjectableResource(projection);
		
		fixture.givenNoPriorActivity()
			.when(new CreateGroupCmd("322", "ExistingGroup"))
			.expectException(IllegalArgumentException.class)
			.expectNoEvents();
	}
	
	@Test
	public void testAddMembers() {
		List<String> users = Arrays.asList("1", "2");
		fixture.given(new GroupCreatedEvt("123", "TestGroup"))
			.when(new AddMembersCmd("123", users))
			.expectSuccessfulHandlerExecution()
			.expectEvents(new MemberAddedEvt("123", users));
	}
	
	@Test
	public void testAddDuplicateMembers() {
		List<String> initalUsers = Arrays.asList("1", "2", "3");
		List<String> newUsers = Arrays.asList("1", "3");
		fixture.given(new GroupCreatedEvt("123", "TestGroup")
					, new MemberAddedEvt("123", initalUsers))
			.when(new AddMembersCmd("123", newUsers))
			.expectException(HasPropertyWithValue.hasProperty("message", equalTo("Members [1, 3] already existing in group [TestGroup]")))
			.expectNoEvents();
	}
}
