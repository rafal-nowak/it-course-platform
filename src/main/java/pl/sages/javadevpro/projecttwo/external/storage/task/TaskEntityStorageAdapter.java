package pl.sages.javadevpro.projecttwo.external.storage.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.task.PageTask;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.task.TaskRepository;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class TaskEntityStorageAdapter implements TaskRepository {

    private final TaskMongoRepository taskRepository;
    private final TaskEntityMapper mapper;

    @Override
    public Task save(Task task) {
        try{
            TaskEntity saved = taskRepository.insert(mapper.toEntity(task));
            log.info("Saved task " + saved);
            return mapper.toDomain(saved);
        }catch (DuplicateKeyException ex){
            log.warning("Task " +  task.getName() + " already exits");
            throw new TaskAlreadyExistsException();
        }
    }

    @Override
    public void remove(final String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> findById(String id) {
        return taskRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageTask findAll(final Pageable pageable) {
        Page<TaskEntity> pageOfTaskEntity = taskRepository.findAll(pageable);
        List<Task> tasksOnCurrentPage = pageOfTaskEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageTask(
                tasksOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfTaskEntity.getTotalPages(),
                pageOfTaskEntity.getTotalElements()
        );
    }

    @Override
    public void update(Task updatedTask) {
        TaskEntity updated = taskRepository.save(mapper.toEntity(updatedTask));
        log.info("Updating task "+ updated);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

}
