package pl.sages.javadevpro.projecttwo.external.storage;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserEntity {

    private Long id;
    private String email;
    private String name;
    private String password;
    private List<String> roles;

}
