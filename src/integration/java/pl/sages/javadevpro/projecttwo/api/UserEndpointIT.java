package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.api.user.UserDto;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRole;


class UserEndpointIT extends BaseIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserService service;

    protected String localUrl(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }

    @Test
    public void should_return_user_by_login() {
        //given
        System.out.println("test");
        User user = new User(
            1L,
            "newUser",
            "pass",
            UserRole.STUDENT
        );
        service.saveUser(user);

        //when
        ResponseEntity<UserDto> response = callGetUser(user.getLogin());

        //then
        UserDto body = response.getBody();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getId(), user.getId());
        Assertions.assertEquals(body.getLogin(), user.getLogin());
        Assertions.assertEquals(body.getPassword(), "######");
        Assertions.assertEquals(body.getRole().toString(), user.getRole().toString());
    }

    private ResponseEntity<UserDto> callGetUser(String userLogin) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        return restTemplate.exchange(
            localUrl("/users/" + userLogin),
            HttpMethod.GET,
            new HttpEntity(headers),
            UserDto.class
        );
    }

    private ResponseEntity<UserDto> callSaveUser(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return restTemplate.exchange(
            localUrl("/users"),
            HttpMethod.POST,
            new HttpEntity(headers),
            UserDto.class
        );
    }
}
