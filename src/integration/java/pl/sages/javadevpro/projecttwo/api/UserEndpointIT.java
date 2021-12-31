package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.api.user.UserDto;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import java.util.List;


class UserEndpointIT extends BaseIT {

    @Autowired
    UserService service;

    @Test
    void admin_should_get_information_about_any_user() {
        //given
        User user = new User(
            "newUser1@example.com",
            "User Name",
            "pass",
            List.of("STUDENT")
        );
        service.saveUser(user);
        String token = getTokenForAdmin();

        //when
        ResponseEntity<UserDto> response = callGetUser(user.getEmail(), token);

        //then
        UserDto body = response.getBody();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getEmail(), user.getEmail());
        Assertions.assertEquals(body.getName(), user.getName());
        Assertions.assertEquals(body.getPassword(), "######");
        Assertions.assertEquals(body.getRoles().toString(), user.getRoles().toString());
    }

    @Test
    void admin_should_be_able_to_save_new_user() {
        //given
        User user = new User(
            "newUser2@example.com",
            "User Name",
            "pass",
            List.of("STUDENT")
        );
        String adminAccessToken = getTokenForAdmin();

        //when
        ResponseEntity<UserDto> response = callSaveUser(user, adminAccessToken);

        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        //and
        UserDto body = response.getBody();
        Assertions.assertEquals(body.getEmail(), user.getEmail());
        Assertions.assertEquals(body.getName(), user.getName());
        Assertions.assertEquals(body.getPassword(), "######");
        Assertions.assertEquals(body.getRoles().toString(), user.getRoles().toString());
    }

    @Test
    void student_should_get_information_about_himself() {
        //given
        User user = new User(
                "newUser3@example.com",
                "User Name",
                "pass",
                List.of("STUDENT")
        );
        service.saveUser(user);
        String accessToken = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        ResponseEntity<UserDto> response = callAboutMe(accessToken);

        //then
        UserDto body = response.getBody();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getEmail(), user.getEmail());
        Assertions.assertEquals(body.getName(), user.getName());
        Assertions.assertEquals(body.getPassword(), "######");
        Assertions.assertEquals(body.getRoles().toString(), user.getRoles().toString());
    }

    @Test
    void student_should_not_get_information_about_other_student() {
        //given
        User user1 = new User(
                "newUser4@example.com",
                "User Name",
                "pass",
                List.of("STUDENT")
        );
        User user2 = new User(
                "oldUser5@example.com",
                "Old User Name",
                "pass",
                List.of("STUDENT")
        );
        service.saveUser(user1);
        service.saveUser(user2);
        String accessToken = getAccessTokenForUser(user1.getEmail(), user1.getPassword());

        //when
        ResponseEntity response = callGetUser(user2.getEmail(), accessToken);

        //then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<UserDto> callGetUser(String email, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return restTemplate.exchange(
            localUrl("/users/" + email),
            HttpMethod.GET,
            new HttpEntity(headers),
            UserDto.class
        );
    }

    private ResponseEntity<UserDto> callAboutMe(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        return restTemplate.exchange(
                localUrl("/users/me"),
                HttpMethod.GET,
                new HttpEntity(headers),
                UserDto.class
        );
    }

    private ResponseEntity<UserDto> callSaveUser(User body, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        return restTemplate.exchange(
            localUrl("/users"),
            HttpMethod.POST,
            new HttpEntity(body, headers),
            UserDto.class
        );
    }
}
