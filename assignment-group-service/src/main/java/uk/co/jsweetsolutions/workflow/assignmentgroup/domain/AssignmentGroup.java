package uk.co.jsweetsolutions.workflow.assignmentgroup.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import uk.co.jsweetsolutions.workflow.assignmentgroup.command.AddMembersCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.command.CreateGroupCmd;
import uk.co.jsweetsolutions.workflow.assignmentgroup.event.GroupCreatedEvt;
import uk.co.jsweetsolutions.workflow.assignmentgroup.event.MemberAddedEvt;

@Aggregate
public class AssignmentGroup {
	@AggregateIdentifier
	private String groupId;
	
	private String name;
	
	private List<String> members;
	
	@CommandHandler
	public AssignmentGroup(CreateGroupCmd cmd) {
		if(!cmd.getName().matches("\\w{3,50}")) {
			throw new IllegalArgumentException("Name " + cmd.getName() + "is not valid");
		}
		AggregateLifecycle.apply(new GroupCreatedEvt(cmd.getGroupId(), cmd.getName()));
	}
	
	@EventSourcingHandler
	public void handle(GroupCreatedEvt evt) {
		this.groupId = evt.getGroupId();
		this.name = evt.getName();
		members = new ArrayList<>();
	}
	
	@CommandHandler
	public void handle(AddMembersCmd cmd) {
		
		List<String> existingMembers = cmd.getUserIds().stream()
				.filter(members::contains)
				.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		if(existingMembers.isEmpty()) {
			members.addAll(cmd.getUserIds());
			AggregateLifecycle.apply(new MemberAddedEvt(cmd.getGroupId(), cmd.getUserIds()));
		}else {
			String ids = existingMembers.stream().collect(Collectors.joining(", "));
			throw new IllegalStateException("Members [" + ids + "] already existing in group [" + this.name + "]");
		}
	}
	
	@EventSourcingHandler
	public void on(MemberAddedEvt evt) {
		this.members.addAll(evt.getUserIds());
	}
}
