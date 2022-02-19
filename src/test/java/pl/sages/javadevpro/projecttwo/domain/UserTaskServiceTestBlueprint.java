package pl.sages.javadevpro.projecttwo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.usertask.DirectoryService;
import pl.sages.javadevpro.projecttwo.domain.usertask.GitService;
import pl.sages.javadevpro.projecttwo.domain.usertask.TaskStatus;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTask;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTaskServiceTestBlueprint {

    private final static String TEST_DIRECTORY = "src/test/usertasktest";

    @Mock private GitService gitService;
    @Mock private DirectoryService directoryService;
    @Mock private UserService userService;
    @Mock private TaskBlueprintService taskBlueprintService;

    @InjectMocks
    private UserTaskService userTaskService;

    private final User fakeUser = new User(
            "email@email.any",
            "user name",
            "pass",
            List.of("STUDENT"),
            new ArrayList<>()
    );
    private final TaskBlueprint fakeTaskBlueprint = new TaskBlueprint(
            "1",
            "task name",
            "description",
            "/path/sample");

    @BeforeEach
    void prepareMocks() {
        when(directoryService.createDirectoryForUserTask(Mockito.any(),Mockito.anyString())).thenReturn(TEST_DIRECTORY);
        when(userService.getUser(Mockito.anyString())).thenReturn(fakeUser);
        when(taskBlueprintService.findBy(Mockito.anyString())).thenReturn(fakeTaskBlueprint);
        when(userService.updateUser(Mockito.any())).thenReturn(fakeUser);
    }


    @DisplayName("Should create new UserTask")
    @Test
    void shouldCreateNewUserTask() {
        String userEmail = fakeUser.getEmail();
        String taskId = fakeTaskBlueprint.getId();

        //when
        UserTask userTask = userTaskService.assignTask(userEmail, taskId);

        //then
        Assertions.assertNotNull(userTask);
        Assertions.assertEquals(fakeTaskBlueprint.getId(),userTask.getId());
        Assertions.assertEquals(fakeTaskBlueprint.getName(),userTask.getName());
        Assertions.assertEquals(fakeTaskBlueprint.getDescription(),userTask.getDescription());
        Assertions.assertEquals(fakeUser.getEmail(), userTask.getUserEmail());
        Assertions.assertEquals(TEST_DIRECTORY,userTask.getUserTaskFolder());
        Assertions.assertEquals(TaskStatus.NOT_STARTED,userTask.getTaskStatus());
    }

}
