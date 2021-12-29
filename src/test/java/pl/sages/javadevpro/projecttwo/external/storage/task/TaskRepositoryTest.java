package pl.sages.javadevpro.projecttwo.external.storage.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sages.javadevpro.projecttwo.domain.TaskService;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.user.User;

import java.util.List;

@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    TaskService service;

    @Test
    public void add_task_test() {
        //given
        Task task1 = new Task(
                "1",
                "Task Name 1",
                "Task Description 1"
        );
        service.saveTask(task1);

        //when
        Task readTask = service.getTask(task1.getId());

        //then
        Assertions.assertEquals(task1.getId(), readTask.getId());
        Assertions.assertEquals(task1.getName(), readTask.getName());
        Assertions.assertEquals(task1.getDescription(), readTask.getDescription());
        Assertions.assertNotEquals(task1, readTask);
    }

    @Test
    public void get_email_should_return_correct_user() {
        //given
        Task task2 = new Task(
                "2",
                "Task Name 2",
                "Task Description 2"
        );
        Task task3 = new Task(
                "3",
                "Task Name 3",
                "Task Description 3"
        );
        Task task4 = new Task(
                "4",
                "Task Name 4",
                "Task Description 4"
        );
        service.saveTask(task2);
        service.saveTask(task3);
        service.saveTask(task4);

        //when
        Task readTask = service.getTask(task3.getId());

        //then
        Assertions.assertEquals(task3.getId(), readTask.getId());
        Assertions.assertEquals(task3.getName(), readTask.getName());
        Assertions.assertEquals(task3.getDescription(), readTask.getDescription());
        Assertions.assertNotEquals(task3, readTask);
    }

    @Test
    public void first_test() {
        System.out.println("UNIT - Test");
    }


}
