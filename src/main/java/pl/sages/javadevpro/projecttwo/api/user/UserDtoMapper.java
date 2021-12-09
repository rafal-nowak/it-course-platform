package pl.sages.javadevpro.projecttwo.api.user;

import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRole;

@Component
public class UserDtoMapper {

    public UserDto toDto(User user) {
        if (user != null) {
            return new UserDto(
                user.getId(),
                user.getLogin(),
                "######",
                user.getRole().name()
            );
        }

        return null;
    }

    public User toDomain(UserDto dto) {
        return new User(
            dto.getId(),
            dto.getLogin(),
            dto.getPassword(),
            UserRole.valueOf(dto.getRole())
        );
    }
}
