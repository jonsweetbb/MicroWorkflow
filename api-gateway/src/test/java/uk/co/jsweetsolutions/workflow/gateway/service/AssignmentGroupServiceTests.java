package uk.co.jsweetsolutions.workflow.gateway.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;
import uk.co.jsweetsolutions.workflow.gateway.config.BaseWebSecurityConfiguration;
import uk.co.jsweetsolutions.workflow.gateway.model.AssignmentGroup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebFluxTest({AssignmentGroupService.class})
@Import({BaseWebSecurityConfiguration.class, AssignmentGroupServiceConfig.class})
public class AssignmentGroupServiceTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void shouldReturnUnauthenticated() throws Exception{
		webTestClient.get()
				.uri("/group")
				.exchange()
				.expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser(username = "1")
	public void shouldReturnNewGroup() throws Exception {
		FluxExchangeResult<AssignmentGroupSummary> result =
				webTestClient.get()
				.uri("/group")
				.exchange()
				.expectStatus().isOk()
				.returnResult(AssignmentGroupSummary.class)
				;

		AssignmentGroupSummary agSummary = (AssignmentGroupSummary) result.getResponseBody().blockFirst();

		assertValues(agSummary);
	}

	@Test
	@WithMockUser(username = "1")
	public void testCreateGroup() throws Exception {
		AssignmentGroup assignmentGroupArg = new AssignmentGroup();
		assignmentGroupArg.setGroupName("TestGroup");

		FluxExchangeResult<AssignmentGroupSummary> result = webTestClient.post()
				.uri("/group")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(assignmentGroupArg), AssignmentGroup.class)
				.exchange()
				.expectStatus().isCreated()
				.returnResult(AssignmentGroupSummary.class);

		AssignmentGroupSummary agSummary = (AssignmentGroupSummary) result.getResponseBody().blockFirst();

		assertValues(agSummary);
	}
	
	private void assertValues(AssignmentGroupSummary agSummary) {
		assertEquals("1", agSummary.getId());
		assertEquals("one", agSummary.getGroupName());
		assertEquals("1", agSummary.getMembers().get(0).getId());
		assertEquals("Bob", agSummary.getMembers().get(0).getForname());
		assertEquals("Smith", agSummary.getMembers().get(0).getSurname());
	}
}
