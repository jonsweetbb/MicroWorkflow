package uk.co.jsweetsolutions.workflow.assignmentgroup.event;

import lombok.Data;

@Data
public class GroupCreatedEvt {
	private String groupId;
	
	private String name;

	public GroupCreatedEvt(String groupId, String name) {
		this.groupId = groupId;
		this.name = name;
	}
}
