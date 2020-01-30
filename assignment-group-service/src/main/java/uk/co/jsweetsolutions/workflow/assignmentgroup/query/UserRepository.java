package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserSummary, String> {

	@Override
	<S extends UserSummary> S save(S entity);

	@Override
	List<UserSummary> findAllById(Iterable<String> ids);
	
}
