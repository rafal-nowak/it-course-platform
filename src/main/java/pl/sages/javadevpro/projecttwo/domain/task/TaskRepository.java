package pl.sages.javadevpro.projecttwo.domain.task;

import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(String id);

    Task save(Task task);

    void update(Task task);

}
