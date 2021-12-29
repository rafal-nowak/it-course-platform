package pl.sages.javadevpro.projecttwo.external.storage.user;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.user.User;

@Mapper
public interface UserEntityMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}
