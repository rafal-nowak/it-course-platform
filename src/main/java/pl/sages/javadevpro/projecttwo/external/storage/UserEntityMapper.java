package pl.sages.javadevpro.projecttwo.external.storage;

import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRole;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
            .login(user.getLogin())
            .password(user.getPassword())
            .role(user.getRole().name())
            .build();
    }

    public User toDomain(UserEntity entity) {
        return new User(
            entity.getId(),
            entity.getLogin(),
            entity.getPassword(),
            UserRole.valueOf(entity.getRole().toUpperCase())
        );
    }
}
