package pl.sages.javadevpro.projecttwo.external.storage.user;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.usertask.TaskStatus;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTask;
import pl.sages.javadevpro.projecttwo.external.storage.usertask.UserTaskEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(User domain);

    User toDomain(UserEntity entity);

}
