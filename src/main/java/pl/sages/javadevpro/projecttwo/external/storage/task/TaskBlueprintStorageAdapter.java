package pl.sages.javadevpro.projecttwo.external.storage.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.task.PageTaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintRepository;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class TaskBlueprintStorageAdapter implements TaskBlueprintRepository {
    private final TaskBlueprintMongoRepository taskRepository;
    private final TaskBlueprintEntityMapper mapper;

    @Override
    public TaskBlueprint save(TaskBlueprint taskBlueprint) {
        try{
            TaskBlueprintEntity saved = taskRepository.insert(mapper.toEntity(taskBlueprint));
            log.info("Saved task blueprint" + saved);
            return mapper.toDomain(saved);
        }catch (DuplicateKeyException ex){
            log.warning("Task blueprint " +  taskBlueprint.getName() + " already exits");
            throw new TaskBlueprintAlreadyExistsException();
        }
    }

    @Override
    public Optional<TaskBlueprint> findById(String id) {
        return taskRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void remove(TaskBlueprint taskBlueprint) {
        taskRepository.deleteById(taskBlueprint.getId());
    }

    @Override
    public void update(TaskBlueprint updatedTaskBlueprint) {
        TaskBlueprintEntity updated = taskRepository.save(mapper.toEntity(updatedTaskBlueprint));
        log.info("Updating task blueprint"+ updated);
    }

    @Override
    public PageTaskBlueprint findAll(final Pageable pageable) {
        Page<TaskBlueprintEntity> pageOfTaskBlueprintEntity = taskRepository.findAll(pageable);
        List<TaskBlueprint> taskBlueprintsOnCurrentPage = pageOfTaskBlueprintEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageTaskBlueprint(
                taskBlueprintsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfTaskBlueprintEntity.getTotalPages(),
                pageOfTaskBlueprintEntity.getTotalElements()
        );
    }
}
