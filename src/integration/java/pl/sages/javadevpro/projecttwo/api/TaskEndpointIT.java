package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
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
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.User;

import java.util.List;

public class TaskEndpointIT extends BaseIT {

    @Autowired
    UserService service;

    @BeforeEach
    void init() {
        addTestUsers();
    }

    @Test
    void should_get_information_about_task() {
        //given
        User user = new User(
                1L,
                "newUser@example.com",
                "User Name",
                "pass",
                List.of("STUDENT")
        );
        service.saveUser(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        ResponseEntity<TaskDto> response = callGetTask(1, token);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
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
}
