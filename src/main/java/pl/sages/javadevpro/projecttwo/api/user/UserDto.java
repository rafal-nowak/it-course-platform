package pl.sages.javadevpro.projecttwo.api.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sages.javadevpro.projecttwo.domain.user.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String email;
    private String name;
    private String password;
    private List<String> roles;


    public static UserDto from(User user) {
        return new UserDto(
            user.getEmail(),
            user.getName(),
            user.getPassword(),
            user.getRoles()
        );
    }
}
