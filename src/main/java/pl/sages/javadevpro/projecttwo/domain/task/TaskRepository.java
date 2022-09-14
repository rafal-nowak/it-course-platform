package pl.sages.javadevpro.projecttwo.domain.task;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(String id);

    PageTask findAll(Pageable pageable);

    Task save(Task task);

    void remove(String id);

    void update(Task task);

    List<Task> findAll();
}
