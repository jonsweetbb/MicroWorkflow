package uk.co.jsweetsolutions.workflow.assignmentgroup.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jsweetsolutions.workflow.assignmentgroup.AssignmentGroupServiceApplication;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.UserRepository;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.UserSummary;
import uk.co.jsweetsolutions.workflow.assignmentgroup.query.UserSummaryFactory;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AssignmentGroupServiceApplication.class)
@ActiveProfiles("test")
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach(){
        userRepository.deleteAll();
    }

    @Test
    public void findById(){
        String userId = UUID.randomUUID().toString();

        Assertions.assertFalse(userRepository.findById(userId).isPresent());

        UserSummary userSummary = UserSummaryFactory.produce(userId);
        userRepository.save(userSummary);

        Optional<UserSummary> result = userRepository.findById(userId);
        Assertions.assertTrue(result.isPresent(), "User summary should be loaded after save");
        Assertions.assertEquals(userSummary, result.get());
    }
}
