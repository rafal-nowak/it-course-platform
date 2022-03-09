package pl.sages.javadevpro.projecttwo.api.usertask;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Mapper
public interface UserTaskDtoMapper {

    UserTaskDto toDto(Task task);

    Task toDomain(UserTaskDto userTaskDto);

}
