package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class AssignmentGroupSummary {
	
	@Id
	private String id;
	private String groupName;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<UserSummary> members;

	public AssignmentGroupSummary(String groupId, String groupName, List<UserSummary> members) {
		super();
		this.id = groupId;
		this.groupName = groupName;
		this.members = members;
	}

	public AssignmentGroupSummary() {
		super();
	}
	
	
}
