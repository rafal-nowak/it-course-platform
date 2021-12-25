package pl.sages.javadevpro.projecttwo.api.user;

import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRole;

@Component
public class UserDtoMapper {

    public UserDto toDto(User user) {
        if (user != null) {
            return new UserDto(
                user.getEmail(),
                user.getName(),
                "######",
                user.getRoles()
            );
        }

        return null;
    }

    public User toDomain(UserDto dto) {
        return new User(
            dto.getEmail(),
            dto.getName(),
            dto.getPassword(),
            dto.getRoles()
        );
    }
}
