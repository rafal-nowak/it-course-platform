package pl.sages.javadevpro.projecttwo.domain.user;

import lombok.*;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTask;

import java.util.ArrayList;
import java.util.List;

@Value
public class User {

    String email;
    String name;
    String password;
    List<String> roles; // TODO <string> to <UserRole>

}
