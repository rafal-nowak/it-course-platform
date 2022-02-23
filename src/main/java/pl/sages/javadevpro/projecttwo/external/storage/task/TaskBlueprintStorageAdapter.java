package pl.sages.javadevpro.projecttwo.external.storage.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import pl.sages.javadevpro.projecttwo.domain.exception.DuplicateRecordException;
import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Log
public class TaskBlueprintStorageAdapter implements TaskBlueprintRepository {
    private final TaskBlueprintMongoRepository taskRepository;
    private final TaskBlueprintEntityMapper mapper;

    @Override
    public Optional<TaskBlueprint> save(TaskBlueprint taskBlueprint) {
        try{
            TaskBlueprintEntity saved = taskRepository.insert(mapper.toEntity(taskBlueprint));
            log.info("Saved task " + saved.toString());
            return Optional.of(mapper.toDomain(saved));
        }catch (DuplicateKeyException ex){
            log.warning("Task " +  taskBlueprint.getName() + " already exits");
            return Optional.empty();
        }
    }

    @Override
    public Optional<TaskBlueprint> findById(String id) {
        return taskRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<TaskBlueprint> remove(TaskBlueprint taskBlueprint) {
        Optional<TaskBlueprintEntity> entity = taskRepository.findById(taskBlueprint.getId());
        TaskBlueprintEntity entityTask = mapper.toEntity(taskBlueprint);
        if(entity.isPresent()) {
            log.info("Removing task " + entityTask.toString());
            taskRepository.delete(entityTask);
            return Optional.of(taskBlueprint);
        }
        return Optional.empty();
    }

    @Override
    public TaskBlueprint update(TaskBlueprint updatedTaskBlueprint) {
        TaskBlueprintEntity updated = taskRepository.save(mapper.toEntity(updatedTaskBlueprint));
        log.info("Updating task "+ updated);
        return mapper.toDomain(updated);
    }
}
