package uk.co.jsweetsolutions.workflow.assignmentgroup.event;

import java.util.List;

import lombok.Data;

@Data
public class MemberAddedEvt {
	private String groupId;
	
	private List<String> userIds;

	public MemberAddedEvt(String groupId, List<String> userIds) {
		this.groupId = groupId;
		this.userIds = userIds;
	}
}
