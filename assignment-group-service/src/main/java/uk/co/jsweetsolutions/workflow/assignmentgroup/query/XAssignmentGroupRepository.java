package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

// TODO rename to AssignmentGroupRepository (causes Spring resource conflict)
public interface XAssignmentGroupRepository extends CrudRepository<AssignmentGroupSummary, String>{
	List<AssignmentGroupSummary> findAll();
	
	Optional<AssignmentGroupSummary> findById(String id);
	
	Optional<AssignmentGroupSummary> findByGroupName(String groupName);
}
