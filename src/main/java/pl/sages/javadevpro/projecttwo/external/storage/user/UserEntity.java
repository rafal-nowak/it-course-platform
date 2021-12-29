package pl.sages.javadevpro.projecttwo.external.storage.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Data
@TypeAlias("Users")
@Document(value = "UserEntity")
public class UserEntity {

    @Id
    private String email;
    private String name;
    private String password;
    private List<String> roles;

}
