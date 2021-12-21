package pl.sages.javadevpro.projecttwo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.sages.javadevpro.projecttwo.config.CredentialsDTO;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.User;


import java.util.List;


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    classes = ProjectTwoApplication.class
)
@ExtendWith(SpringExtension.class)
public class BaseIT {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected UserService userService;

    private static User adminUser = new User(
            1_000_999L,
            "admin@example.pl",
            "Stefan Burczymucha",
            "password",
            List.of("ADMIN")
        );

    protected String localUrl(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }

    protected void addTestUsers() {
        userService.saveUser(adminUser);
    }

    protected String getTokenForUser(String email, String password) {
        CredentialsDTO body = new CredentialsDTO();
        body.setEmail(email);
        body.setPassword(password);
        return singIn(body);
    }

    protected String getTokenForAdmin() {
        CredentialsDTO body = new CredentialsDTO();
        body.setEmail(adminUser.getEmail());
        body.setPassword(adminUser.getPassword());
        return singIn(body);
    }

    private String singIn(CredentialsDTO body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        ResponseEntity<Void> response = restTemplate.exchange(
            localUrl("/login"),
            HttpMethod.POST,
            new HttpEntity(body, headers),
            void.class
        );
        return response.getHeaders().getFirst("Authorization");
    }

}
