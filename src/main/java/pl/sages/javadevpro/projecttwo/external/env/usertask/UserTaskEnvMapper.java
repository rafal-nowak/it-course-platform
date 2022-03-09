package pl.sages.javadevpro.projecttwo.external.env.usertask;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Mapper(componentModel = "spring")
public interface UserTaskEnvMapper {

    UserTaskEnv toDto(Task task);

    Task toDomain(UserTaskEnv userTaskExec);

}
