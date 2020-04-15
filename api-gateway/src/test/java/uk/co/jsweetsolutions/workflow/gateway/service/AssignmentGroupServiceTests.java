package uk.co.jsweetsolutions.workflow.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;

@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentGroupServiceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldReturnUnauthenticated() throws Exception{
		this.mockMvc
			.perform(get("/group"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void shouldReturnNewGroup() throws Exception {
		MvcResult result = this.mockMvc
			.perform(get("/group").with(httpBasic("1", "password")))
			.andExpect(status().is2xxSuccessful())
			.andReturn();
		
		AssignmentGroupSummary agSummary = (AssignmentGroupSummary) result.getAsyncResult();
		
		assertValues(agSummary);
	}
	
	@Test
	public void testCreateGroup() throws Exception {
		MvcResult result = this.mockMvc
				.perform(post("/group")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"groupName\":\"TestGroup\"}")
				.with(httpBasic("1", "password")))
				.andReturn();
		
		AssignmentGroupSummary agSummary = (AssignmentGroupSummary) result.getAsyncResult();
		
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
