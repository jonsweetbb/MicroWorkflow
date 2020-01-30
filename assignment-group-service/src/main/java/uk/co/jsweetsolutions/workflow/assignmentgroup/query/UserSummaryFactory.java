package uk.co.jsweetsolutions.workflow.assignmentgroup.query;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserSummaryFactory {
	
	// TODO load users from AD
	private static final List<String> FIRST_NAMES = Arrays.asList("Guybrush", "Elaine", "Largo", "Herman", "Stan", "Wally");
	private static final List<String> LAST_NAMES = Arrays.asList("Threepwood", "Marley", "Legrand", "Toothrot", "Honestman", "Cartman");
	
	private UserSummaryFactory() {
		
	}
	
	public static UserSummary produce(String id) {
		Random random = new Random();
		UserSummary user = new UserSummary();
		user.setId(id);
		user.setForname(FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())));
		user.setSurname(LAST_NAMES.get(random.nextInt(LAST_NAMES.size())));
		return user;
	}

}
