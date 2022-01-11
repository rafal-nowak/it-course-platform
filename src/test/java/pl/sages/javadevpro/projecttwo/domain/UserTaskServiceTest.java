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
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.usertask.DirectoryService;
import pl.sages.javadevpro.projecttwo.domain.usertask.GitService;
import pl.sages.javadevpro.projecttwo.domain.usertask.TaskStatus;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTask;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTaskServiceTest {

    private final static String TEST_DIRECTORY = "src/test/usertasktest";

    @Mock private GitService gitService;
    @Mock private DirectoryService directoryService;
    @Mock private UserService userService;

    @InjectMocks
    private UserTaskService userTaskService;

    @BeforeEach
    void prepareMocks() {
        User fakeUser = new User();
        fakeUser.setTasks(new ArrayList<>());

        when(directoryService.createDirectoryForUserTask(Mockito.any(),Mockito.anyString())).thenReturn(TEST_DIRECTORY);
        when(userService.updateUser(Mockito.any())).thenReturn(fakeUser);
    }


    @DisplayName("Should create new UserTask")
    @Test
    void shouldCreateNewUserTask() {
        //given
        Task sampleTask = new Task(
                "1",
                "task1",
                "description",
                "https://githb.com/sample"
        );
        User sampleUser = new User();
        sampleUser.setTasks(new ArrayList<>());
        sampleUser.setEmail("user@email.com");

        //when
        UserTask userTask = userTaskService.assignTask(sampleUser, sampleTask);

        //then
        Assertions.assertNotNull(userTask);
        Assertions.assertEquals(sampleTask.getId(),userTask.getId());
        Assertions.assertEquals(sampleTask.getName(),userTask.getName());
        Assertions.assertEquals(sampleTask.getDescription(),userTask.getDescription());
        Assertions.assertEquals(sampleUser.getEmail(), userTask.getUserEmail());
        Assertions.assertEquals(TEST_DIRECTORY,userTask.getUserTaskFolder());
        Assertions.assertEquals(TaskStatus.NOT_STARTED,userTask.getTaskStatus());
    }

}
