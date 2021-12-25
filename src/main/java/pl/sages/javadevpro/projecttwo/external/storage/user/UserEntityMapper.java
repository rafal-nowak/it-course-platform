package pl.sages.javadevpro.projecttwo.external.storage.user;

import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;

@Component
public class UserEntityMapper {


    public UserEntity toEntity(User user) {
        return UserEntity.builder()
            .email(user.getEmail())
            .name(user.getName())
            .password(user.getPassword())
            .roles(user.getRoles())
            .build();
    }

    public User toDomain(UserEntity entity) {
        return new User(
            entity.getEmail(),
            entity.getName(),
            entity.getPassword(),
            entity.getRoles()
        );
    }
}
