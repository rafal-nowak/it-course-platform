package pl.sages.javadevpro.projecttwo.external.storage.task;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository  extends CrudRepository<TaskEntity, Integer> {
}
