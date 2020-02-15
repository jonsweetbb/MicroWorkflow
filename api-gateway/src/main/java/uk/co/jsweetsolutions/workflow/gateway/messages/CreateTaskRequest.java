package uk.co.jsweetsolutions.workflow.gateway.messages;

public class CreateTaskRequest {
	private final String taskName;
	private final String ownerGroupId;
	private final String assigneeGroupId;

	public CreateTaskRequest(String taskName, String ownerGroupId, String assigneeGroupId) {
		super();
		this.taskName = taskName;
		this.ownerGroupId = ownerGroupId;
		this.assigneeGroupId = assigneeGroupId;
	}

	public String getOwnerGroupId() {
		return ownerGroupId;
	}

	public String getAssigneeGroupId() {
		return assigneeGroupId;
	}

	public String getTaskName() {
		return taskName;
	}
	
}
