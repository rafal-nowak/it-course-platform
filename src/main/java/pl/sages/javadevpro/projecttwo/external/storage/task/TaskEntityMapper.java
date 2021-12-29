package pl.sages.javadevpro.projecttwo.external.storage.task;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.task.Task;

@Mapper
public interface TaskEntityMapper {

    TaskEntity toEntity(Task task);

    Task toDomain(TaskEntity entity);

}
