package uk.co.jsweetsolutions.workflow.assignmentgroup.command;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class AddMembersCmd {
	@TargetAggregateIdentifier
	private String groupId;
	
	private List<String> userIds;

	public AddMembersCmd(String groupId, List<String> userIds) {
		super();
		this.groupId = groupId;
		this.userIds = userIds;
	}
	
}
