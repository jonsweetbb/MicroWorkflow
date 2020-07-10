package uk.co.jsweetsolutions.workflow.tasks.integration;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jsweetsolutions.workflow.task.TaskServiceApplication;
import uk.co.jsweetsolutions.workflow.task.configuration.DbConfig;
import uk.co.jsweetsolutions.workflow.task.query.TaskState;
import uk.co.jsweetsolutions.workflow.task.query.TaskSummary;
import uk.co.jsweetsolutions.workflow.task.query.TaskSummaryProjection;
import uk.co.jsweetsolutions.workflow.task.query.TasksRepository;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {TaskServiceApplication.class})
@ActiveProfiles("test")
public class TaskSummaryProjectionIT {

    @Autowired
    private TasksRepository tasksRepository;

    @BeforeEach
    public void beforeEach(){
        log.info("Deleting all task records before test");
        tasksRepository.deleteAll();
    }

    @Test
    public void findById(){
        UUID taskId = UUID.randomUUID();

        Assertions.assertFalse(tasksRepository.findById(taskId.toString()).isPresent(), "Task should not be fetchable before save");

        TaskSummary taskSummary = new TaskSummary(taskId.toString(), LocalDateTime.now(), LocalDateTime.now(), TaskState.ASSIGNED);
        tasksRepository.save(taskSummary);

        Optional<TaskSummary> result = tasksRepository.findById(taskId.toString());
        Assertions.assertTrue(result.isPresent(), "Task Summary not loaded after save");
        Assertions.assertEquals(taskSummary, result.get(), "Saved and loaded tasks differ");
    }

    @Test
    public void findAllById(){
        String taskOneId = UUID.randomUUID().toString();
        String taskTwoId = UUID.randomUUID().toString();
        List<String> taskIds = new ArrayList<>();
        Collections.addAll(taskIds, taskOneId, taskTwoId);

        List<TaskSummary> initialResult = tasksRepository.findAllById(taskIds);
        Assertions.assertEquals(0, initialResult.size(), "Tasks fetched before save");

        TaskSummary taskOne = TaskSummary.builder()
                .id(taskOneId)
                .createdOn(LocalDateTime.now())
                .lastUpdatedOn(LocalDateTime.now())
                .state(TaskState.ASSIGNED)
                .build();

        TaskSummary taskTwo = TaskSummary.builder()
                .id(taskTwoId)
                .createdOn(LocalDateTime.now())
                .lastUpdatedOn(LocalDateTime.now())
                .state(TaskState.CLOSED)
                .build();

        tasksRepository.save(taskOne);
        tasksRepository.save(taskTwo);

        List<TaskSummary> secondResult = tasksRepository.findAllById(taskIds);
        Assertions.assertEquals(2, secondResult.size(), "Fetch returned unexpected count");
        Assertions.assertEquals(taskOne, secondResult.get(0));
        Assertions.assertEquals(taskTwo, secondResult.get(1));
    }
}
