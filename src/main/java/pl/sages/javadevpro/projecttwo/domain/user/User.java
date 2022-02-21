package pl.sages.javadevpro.projecttwo.domain.user;

import lombok.*;

import java.util.List;

@Value
public class User {

    String id;
    String email;
    String name;
    String password;
    List<String> roles; // TODO <string> to <UserRole>

}
