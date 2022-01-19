package pl.sages.javadevpro.projecttwo.api.usertask;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTask;

@Mapper
public interface UserTaskDtoMapper {

    UserTaskDto toDto(UserTask userTask);

    UserTask toDomain(UserTaskDto userTaskDto);

}
