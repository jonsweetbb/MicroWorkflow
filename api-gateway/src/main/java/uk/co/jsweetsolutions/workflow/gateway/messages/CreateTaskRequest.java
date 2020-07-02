package uk.co.jsweetsolutions.workflow.gateway.messages;

public class CreateTaskRequest {
	private String taskName = null;
	private String ownerGroupId = null;
	private String assigneeGroupId = null;

	public CreateTaskRequest(){

	}

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

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setOwnerGroupId(String ownerGroupId) {
		this.ownerGroupId = ownerGroupId;
	}

	public void setAssigneeGroupId(String assigneeGroupId) {
		this.assigneeGroupId = assigneeGroupId;
	}
}
