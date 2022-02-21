package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.api.task.TaskBlueprintDto;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;

import java.util.List;


public class TaskBlueprintControllerIT extends BaseIT {

    @Autowired
    UserService userService;
    @Autowired
    TaskBlueprintService taskBlueprintService;

    @Test
    void should_get_information_about_task() {
        //given
        User user = new User(
                null,
                "newUser@example.com",
                "User Name",
                "pass",
                List.of("STUDENT")
        );
        TaskBlueprint taskBlueprint = new TaskBlueprint(
                "1",
                "Task Name 1",
                "Task description 1",
                "https://github.com/some-reporitory-1"
        );
        userService.save(user);
        taskBlueprintService.save(taskBlueprint);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        //when
        ResponseEntity<TaskBlueprintDto> response = callGetTask(1, token);

        //then
        TaskBlueprintDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(taskBlueprint.getId(), body.getId());
        Assertions.assertEquals(taskBlueprint.getName(), body.getName());
        Assertions.assertEquals(taskBlueprint.getDescription(), body.getDescription());
        Assertions.assertEquals(taskBlueprint.getRepositoryUrl(),body.getRepositoryUrl());
    }

    @Test
    void should_get_information_about_correct_task() {
        //given
        User user = new User(
                "ID4",
                "newUser1@example.com",
                "User Name1",
                "pass1",
                List.of("STUDENT")
        );
        TaskBlueprint taskBlueprint2 = new TaskBlueprint(
                "2",
                "Task Name 2",
                "Task description 2",
                "https://github.com/some-reporitory-2"
        );
        TaskBlueprint taskBlueprint3 = new TaskBlueprint(
                "3",
                "Task Name 3",
                "Task description 3",
                "https://github.com/some-reporitory-3"
        );
        TaskBlueprint taskBlueprint4 = new TaskBlueprint(
                "4",
                "Task Name 4",
                "Task description 4",
                "https://github.com/some-reporitory-4"
        );
        userService.save(user);
        taskBlueprintService.save(taskBlueprint2);
        taskBlueprintService.save(taskBlueprint3);
        taskBlueprintService.save(taskBlueprint4);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        ResponseEntity<TaskBlueprintDto> response = callGetTask(3, token);

        //then
        TaskBlueprintDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(taskBlueprint3.getId(), body.getId());
        Assertions.assertEquals(taskBlueprint3.getName(), body.getName());
        Assertions.assertEquals(taskBlueprint3.getDescription(), body.getDescription());
        Assertions.assertEquals(taskBlueprint3.getRepositoryUrl(), body.getRepositoryUrl());
    }

    @Test
     void admin_should_be_able_to_save_new_task() {
        //given
        TaskBlueprint taskBlueprint5 = new TaskBlueprint(
                "5",
                "Task Name 5",
                "Task description 5",
                "https://github.com/some-reporitory-5"
        );
        String adminAccessToken = getTokenForAdmin();
        //when
        ResponseEntity<TaskBlueprintDto> response = callSaveTask(taskBlueprint5, adminAccessToken);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        TaskBlueprintDto body = response.getBody();
        Assertions.assertEquals(taskBlueprint5.getId(), body.getId());
        Assertions.assertEquals(taskBlueprint5.getName(), body.getName());
        Assertions.assertEquals(taskBlueprint5.getDescription(), body.getDescription());
        Assertions.assertEquals(taskBlueprint5.getRepositoryUrl(), body.getRepositoryUrl());
    }

    @Test
    void student_should_not_be_able_to_save_new_task() {
        //given
        User user = new User(
                "ID5",
                "newUser1@example.com",
                "User Name1",
                "pass1",
                List.of("STUDENT")
        );
        TaskBlueprint taskBlueprint5 = new TaskBlueprint(
                "5",
                "Task Name 5",
                "Task description 5",
                "/path/xxx"
        );
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        //when
        ResponseEntity<TaskBlueprintDto> response = callSaveTask(taskBlueprint5, token);
        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void should_return_conflict_about_duplicated_task(){
        //given
        TaskBlueprint taskBlueprint9 = new TaskBlueprint(
                "9",
                "Task Name 9",
                "Task description 9",
                "/repo/path"
        );
        String adminAccessToken = getTokenForAdmin();
        taskBlueprintService.save(taskBlueprint9);
        //when
        ResponseEntity<TaskBlueprintDto> response = callSaveTask(taskBlueprint9,adminAccessToken);
        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    void admin_should_be_able_to_delete_task() {
        //given
        TaskBlueprint taskBlueprint6 = new TaskBlueprint(
                "6",
                "Task Name 6",
                "Task description 6",
                "/repo/path"
        );
        String adminAccessToken = getTokenForAdmin();
        taskBlueprintService.save(taskBlueprint6);
        //when
        ResponseEntity<Void> response = callDeleteTask(taskBlueprint6, adminAccessToken);
        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void student_should_not_be_able_to_delete_task() {
        //given
        User user = new User(
                "ID6",
                "newUser@example.com",
                "User Name",
                "pass",
                List.of("STUDENT")
        );
        TaskBlueprint taskBlueprint6 = new TaskBlueprint(
                "6",
                "Task Name 6",
                "Task description 6",
                "/path/path"
        );
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        taskBlueprintService.save(taskBlueprint6);

        //when
        ResponseEntity<Void> response = callDeleteTask(taskBlueprint6, token);

        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void admin_should_be_able_to_update_task() {
        //given
        TaskBlueprint taskBlueprint7 = new TaskBlueprint(
                "7",
                "Task Name 7",
                "Task description 7",
                "/new/path"
        );
        TaskBlueprint updatedTaskBlueprint = new TaskBlueprint(
                "7",
                "Task Name 7 is updated",
                "Task 7 description is updated ",
                "/no/idea/path"
        );
        String adminAccessToken = getTokenForAdmin();
        taskBlueprintService.save(taskBlueprint7);
        //when
        ResponseEntity<TaskBlueprintDto> response = callUpdateTask(updatedTaskBlueprint, adminAccessToken);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        TaskBlueprintDto body = response.getBody();
        Assertions.assertEquals(taskBlueprint7.getId(), body.getId());
        Assertions.assertEquals(updatedTaskBlueprint.getName(), body.getName());
        Assertions.assertEquals(updatedTaskBlueprint.getDescription(), body.getDescription());
    }

    @Test
    void should_get_response_code_204_when_task_not_exits() {
        //given
        User user = new User(
                "ID7",
                "newUser1@example.com",
                "User Name1",
                "pass1",
                List.of("STUDENT")
        );
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        //when
        ResponseEntity<TaskBlueprintDto> response = callGetTask(1,token);
        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }



    private ResponseEntity<TaskBlueprintDto> callGetTask(int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return restTemplate.exchange(
                localUrl("/task-blueprints/" + id),
                HttpMethod.GET,
                new HttpEntity(headers),
                TaskBlueprintDto.class
        );
    }

    private ResponseEntity<TaskBlueprintDto> callSaveTask(TaskBlueprint body, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        return restTemplate.exchange(
                localUrl("/task-blueprints"),
                HttpMethod.POST,
                new HttpEntity(body, headers),
                TaskBlueprintDto.class
        );
    }

    private ResponseEntity<Void> callDeleteTask(TaskBlueprint body, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        return restTemplate.exchange(
                localUrl("/task-blueprints"),
                HttpMethod.DELETE,
                new HttpEntity(body, headers),
                Void.class
        );
    }

    private ResponseEntity<TaskBlueprintDto> callUpdateTask(TaskBlueprint body, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        return restTemplate.exchange(
                localUrl("/task-blueprints"),
                HttpMethod.PUT,
                new HttpEntity(body, headers),
                TaskBlueprintDto.class
        );
    }
}
