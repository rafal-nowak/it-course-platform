package pl.sages.javadevpro.projecttwo.external.storage.task;


import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Mapper(componentModel = "spring")
public interface TaskEntityMapper {

    TaskEntity toEntity(Task domain);

    Task toDomain(TaskEntity entity);

}
