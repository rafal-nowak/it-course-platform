package pl.sages.javadevpro.projecttwo.api.user;

import lombok.Value;

import java.util.List;

@Value
public class UserDto {

    String email;
    String name;
    String password;
    List<String> roles;

}
