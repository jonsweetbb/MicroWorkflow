package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import lombok.Data;

@Entity
@Data
public class AssignmentGroupSummary {
	
	@Id
	@Column(name = "assignment_group_summary_id")
	private String id;
	private String groupName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name="group_members",
			joinColumns = @JoinColumn(referencedColumnName = "ASSIGNMENT_GROUP_SUMMARY_ID"),
			inverseJoinColumns = @JoinColumn(referencedColumnName = "USER_SUMMARY_ID"))
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
