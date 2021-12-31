package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.api.task.TaskDto;
import pl.sages.javadevpro.projecttwo.api.user.UserDto;
import pl.sages.javadevpro.projecttwo.domain.TaskService;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.user.User;

import java.util.List;

public class TaskEndpointIT extends BaseIT {

    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;

    @Test
    void should_get_information_about_task() {
        //given
        User user = new User(
                "newUser@example.com",
                "User Name",
                "pass",
                List.of("STUDENT")
        );
        Task task = new Task(
                "1",
                "Task Name 1",
                "Task description 1"
        );
        userService.saveUser(user);
        taskService.saveTask(task);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        ResponseEntity<TaskDto> response = callGetTask(1, token);

        //then
        TaskDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals( task.getId(), body.getId());
        Assertions.assertEquals(task.getName(), body.getName());
        Assertions.assertEquals(task.getDescription(), body.getDescription());
    }

    @Test
    void should_get_information_about_correct_task() {
        //given
        User user = new User(
                "newUser1@example.com",
                "User Name1",
                "pass1",
                List.of("STUDENT")
        );
        Task task2 = new Task(
                "2",
                "Task Name 2",
                "Task description 2"
        );
        Task task3 = new Task(
                "3",
                "Task Name 3",
                "Task description 3"
        );
        Task task4 = new Task(
                "4",
                "Task Name 4",
                "Task description 4"
        );
        userService.saveUser(user);
        taskService.saveTask(task2);
        taskService.saveTask(task3);
        taskService.saveTask(task4);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        ResponseEntity<TaskDto> response = callGetTask(3, token);

        //then
        TaskDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(task3.getId(), body.getId());
        Assertions.assertEquals(task3.getName(), body.getName());
        Assertions.assertEquals(task3.getDescription(), body.getDescription());
    }

    @Test
    void admin_should_be_able_to_save_new_task() {
        //given
        Task task5 = new Task(
                "5",
                "Task Name 5",
                "Task description 5"
        );
        String adminAccessToken = getTokenForAdmin();

        //when
        ResponseEntity<TaskDto> response = callSaveTask(task5, adminAccessToken);

        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        //and
        TaskDto body = response.getBody();
        Assertions.assertEquals(body.getId(), task5.getId());
        Assertions.assertEquals(body.getName(), task5.getName());
        Assertions.assertEquals(body.getDescription(), task5.getDescription());
    }

    private ResponseEntity<TaskDto> callGetTask(int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return restTemplate.exchange(
                localUrl("/tasks/" + id),
                HttpMethod.GET,
                new HttpEntity(headers),
                TaskDto.class
        );
    }

    private ResponseEntity<TaskDto> callSaveTask(Task body, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        return restTemplate.exchange(
                localUrl("/tasks"),
                HttpMethod.POST,
                new HttpEntity(body, headers),
                TaskDto.class
        );
    }
}
