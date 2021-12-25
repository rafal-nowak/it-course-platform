package pl.sages.javadevpro.projecttwo.external.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.task.TaskRepository;
import pl.sages.javadevpro.projecttwo.external.storage.task.JpaTaskRepository;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskEntity;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskEntityMapper;

import java.util.Optional;

@RequiredArgsConstructor
@Log
public class TaskStorageAdapter implements TaskRepository {
    private final JpaTaskRepository taskRepository;
    private final TaskEntityMapper mapper;

    @Override
    public Task save(Task task) {
        TaskEntity saved = taskRepository.save(mapper.toEntity(task));
        log.info("Saved task " + saved.toString());
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        Optional<TaskEntity> entity = taskRepository.findById(id);
        log.info("Found task " + entity.map(Object::toString).orElse("none"));
        if (entity.isPresent()) {
            return entity
                    .map(mapper::toDomain);
        }

        return Optional.empty();
    }

}
