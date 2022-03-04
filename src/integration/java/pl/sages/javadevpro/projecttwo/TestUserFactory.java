package pl.sages.javadevpro.projecttwo;

import pl.sages.javadevpro.projecttwo.domain.user.User;

import java.util.List;

public class TestUserFactory {

    private static int userSequence = 0;

    public static User createStudent() {
        userSequence++;
        return new User(
                "TEST" + userSequence,
                "newUser" + userSequence + "@example.com",
                "User Name " + userSequence,
                "password",
                List.of("STUDENT")
                );
    }



}
