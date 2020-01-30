package uk.co.jsweetsolutions.workflow.gateway.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.jsweetsolutions.workflow.assignmentgroup.command.CreateGroupCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupByIdQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;
import uk.co.jsweetsolutions.workflow.gateway.model.AssignmentGroup;

@RestController
public class AssginmentGroupService {

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private QueryGateway queryGateway;
	
	@PostMapping(path = "/group")
	public CompletableFuture<AssignmentGroupSummary> createGroup(@RequestBody AssignmentGroup group){
		String groupId = UUID.randomUUID().toString();
		CreateGroupCmd cmd = new CreateGroupCmd(groupId, group.getGroupName());
		String result = commandGateway.sendAndWait(cmd);
		return result == null ? null : getAssignmentGroup(groupId);
	}
	
	@GetMapping(path = "/group/{id}")
	public CompletableFuture<AssignmentGroupSummary> getAssignmentGroup(@PathVariable(name = "id") String id){
		AssignmentGroupByIdQuery qry = new AssignmentGroupByIdQuery();
		qry.setGroupId(id);
		return queryGateway.query(qry, AssignmentGroupSummary.class);
	}
	
}
