package uk.co.jsweetsolutions.workflow.gateway.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import uk.co.jsweetsolutions.workflow.assignmentgroup.command.AddMembersCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.command.CreateGroupCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupByIdQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummariesQuery;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.AssignmentGroupSummary;
import uk.co.jsweetsolutions.workflow.gateway.model.AssignmentGroup;
import uk.co.jsweetsolutions.workflow.gateway.model.WorkflowUser;

@RestController
public class AssignmentGroupService {

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private QueryGateway queryGateway;
	
	@PostMapping(path = "/group")
	@ResponseStatus(HttpStatus.CREATED)
	public CompletableFuture<AssignmentGroupSummary> createGroup(@RequestBody AssignmentGroup group){
		String groupId = UUID.randomUUID().toString();
		CreateGroupCmd cmd = new CreateGroupCmd(groupId, group.getGroupName());
		String result = commandGateway.sendAndWait(cmd);
		return result == null ? null : getAssignmentGroup(groupId);
	}
	
	@PostMapping(path = "/group/{id}")
	public CompletableFuture<AssignmentGroupSummary> addUsersToGroup(@RequestBody String[] users, @PathVariable(name = "id") String id){
		AddMembersCmd cmd = new AddMembersCmd(id, Arrays.asList(users));
		Object result = commandGateway.sendAndWait(cmd, 10000, TimeUnit.MILLISECONDS);
		return result == null ? null : getAssignmentGroup(id);
	}
	
	@GetMapping(path = "/group/{id}")
	public CompletableFuture<AssignmentGroupSummary> getAssignmentGroup(@PathVariable(name = "id") String id){
		AssignmentGroupByIdQuery qry = new AssignmentGroupByIdQuery();
		qry.setGroupId(id);
		return queryGateway.query(qry, ResponseTypes.instanceOf(AssignmentGroupSummary.class));
	}
	
	@GetMapping(path = "/group")
	public CompletableFuture<List<AssignmentGroupSummary>> getAssignmentGroups(){
		AssignmentGroupSummariesQuery qry = new AssignmentGroupSummariesQuery(0, 0);
		return queryGateway.query(qry, ResponseTypes.multipleInstancesOf(AssignmentGroupSummary.class));
	}
	
}
