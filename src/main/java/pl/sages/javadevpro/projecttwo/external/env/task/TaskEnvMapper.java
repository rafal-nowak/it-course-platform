package pl.sages.javadevpro.projecttwo.external.env.task;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Mapper(componentModel = "spring")
public interface TaskEnvMapper {

    TaskEnv toDto(Task task);

    Task toDomain(TaskEnv userTaskExec);

}
