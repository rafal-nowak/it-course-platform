package pl.sages.javadevpro.projecttwo.domain.task;

import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(String id);

    void remove(Task task);

    void update(Task task);
}
