package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.jsweetsolutions.workflow.assignmentgroup.event.GroupCreatedEvt;
import uk.co.jsweetsolutions.workflow.assignmentgroup.event.MemberAddedEvt;

@Component
public class AssignmentGroupSummaryProjection {
	
	@Autowired(required = true)
	private XAssignmentGroupRepository assignmentGroupRepository;
	
	@Autowired(required = true)
	private UserRepository userRepository;
	
	@EventHandler
	public void on(GroupCreatedEvt evt) {
		AssignmentGroupSummary entity = new AssignmentGroupSummary(evt.getGroupId(), evt.getName(), new ArrayList<>());
		assignmentGroupRepository.save(entity);
	}
	
	@EventHandler
	public void on(MemberAddedEvt evt) {
		Optional<AssignmentGroupSummary> groupEntity = assignmentGroupRepository.findById(evt.getGroupId());
		if(!groupEntity.isPresent()) {
			throw new IllegalArgumentException("Group [" + evt.getGroupId() + "] does not exist");
		}
		
		List<UserSummary> existingUsers = userRepository.findAllById(evt.getUserIds());
		
		List<UserSummary> missingUsers = evt.getUserIds().stream()
						.map(UserSummaryFactory::produce)
						.filter(user -> !existingUsers.contains(user))
						.collect(Collectors.toList());
		
		groupEntity.get().getMembers().addAll(existingUsers);
		groupEntity.get().getMembers().addAll(missingUsers);
		
		assignmentGroupRepository.save(groupEntity.get());
	}
	
	@QueryHandler
	public List<AssignmentGroupSummary> handle(AssignmentGroupSummariesQuery qry){
		return assignmentGroupRepository.findAll();
	}
	
	@QueryHandler
	public Optional<AssignmentGroupSummary> handle(AssignmentGroupByIdQuery qry){
		return assignmentGroupRepository.findById(qry.getGroupId());
	}
	
	public Optional<AssignmentGroupSummary> handle(AssignmentGroupByNameQuery qry){
		return assignmentGroupRepository.findByGroupName(qry.getGroupName());
	}
}
