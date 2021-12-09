package pl.sages.javadevpro.projecttwo.api.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sages.javadevpro.projecttwo.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private String role;


    public static UserDto from(User user) {
        return new UserDto(
            user.getId(),
            user.getLogin(),
            "######",
            user.getRole().name()
        );
    }
}
