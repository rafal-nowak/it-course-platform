package pl.sages.javadevpro.projecttwo.external.storage.task;


import pl.sages.javadevpro.projecttwo.domain.task.Task;

public interface TaskEntityMapper {

    TaskEntity toEntity(Task domain);

    Task toDomain(TaskEntity entity);

}
