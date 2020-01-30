package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserSummary {
	@Id
	private String id;
	
	private String forname;
	
	private String surname;

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
