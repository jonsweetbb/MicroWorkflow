package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class UserSummary {
	@Id
	@Column(name = "user_summary_id")
	private String id;
	
	private String forname;
	
	private String surname;
	
	@ManyToMany(mappedBy = "members")
	private transient List<AssignmentGroupSummary> memberOf;

	public UserSummary(String id, String forname, String surname) {
		super();
		this.id = id;
		this.forname = forname;
		this.surname = surname;
	}

	public UserSummary() {
		super();
	}
	
	
}
