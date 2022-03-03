package pl.sages.javadevpro.projecttwo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRole;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;

import java.util.List;

public class UserServiceIT extends BaseIT {

    @Autowired
    UserService service;

    @Test
    public void add_user_test() {
        //given
        User user = new User(
                null,
                "newUser@example.com",
                "User Name",
                "pass",
                List.of(UserRole.STUDENT)
        );
        User saved = service.save(user);

        //when
        User readUser = service.findById(saved.getId());

        //then
        Assertions.assertEquals(user.getEmail(), readUser.getEmail());
        Assertions.assertEquals(user.getName(), readUser.getName());
        Assertions.assertEquals(user.getPassword(), readUser.getPassword());

    }

    @Test
    public void get_id_should_return_correct_user() {
        //given
        User user1 = new User(
                null,
                "newUser1@example.com",
                "User Name 1",
                "pass1",
                List.of(UserRole.STUDENT)
        );
        User user2 = new User(
                null,
                "newUser2@example.com",
                "User Name 2",
                "pass2",
                List.of(UserRole.STUDENT)
        );
        User user3 = new User(
                null,
                "newUser3@example.com",
                "User Name 3",
                "pass3",
                List.of(UserRole.STUDENT)
        );
        service.save(user1);
        User saved = service.save(user2);
        service.save(user3);

        //when
        User readUser = service.findById(saved.getId());

        //then
        Assertions.assertEquals(user2.getEmail(), readUser.getEmail());
        Assertions.assertEquals(user2.getName(), readUser.getName());
        Assertions.assertEquals(user2.getPassword(), readUser.getPassword());
    }

}
