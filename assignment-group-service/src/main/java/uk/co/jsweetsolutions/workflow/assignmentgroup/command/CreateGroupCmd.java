package uk.co.jsweetsolutions.workflow.assignmentgroup.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CreateGroupCmd {
	@TargetAggregateIdentifier
	private String groupId;
	
	private String name;

	public CreateGroupCmd(String groupId, String name) {
		this.groupId = groupId;
		this.name = name;
	}
}
