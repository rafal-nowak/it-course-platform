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
    public TaskBlueprint save(TaskBlueprint taskBlueprint) {
        try{
            TaskBlueprintEntity saved = taskRepository.insert(mapper.toEntity(taskBlueprint));
            log.info("Saved task " + saved.toString());
            return mapper.toDomain(saved);
        }catch (DuplicateKeyException ex){
            log.warning("Task " +  taskBlueprint.getName() + " already exits");
            throw new DuplicateRecordException("Task already exits");
        }
    }

    @Override
    public Optional<TaskBlueprint> findById(String id) {
        Optional<TaskBlueprintEntity> entity = taskRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RecordNotFoundException("Task not found");
        }
        log.info("Found task " + entity.map(Object::toString));
        return entity
                .map(mapper::toDomain);
    }

    @Override
    public void remove(TaskBlueprint taskBlueprint) {
        Optional<TaskBlueprintEntity> entity = taskRepository.findById(taskBlueprint.getId());
        TaskBlueprintEntity entityTask = mapper.toEntity(taskBlueprint);
        if(entity.isPresent()) {
            log.info("Removing task " + entityTask.toString());
            taskRepository.delete(entityTask);
        }
    }

    @Override
    public TaskBlueprint update(TaskBlueprint updatedTaskBlueprint) {
        TaskBlueprintEntity updated = taskRepository.save(mapper.toEntity(updatedTaskBlueprint));
        log.info("Updating task "+ updated);
        return mapper.toDomain(updated);
    }
}
