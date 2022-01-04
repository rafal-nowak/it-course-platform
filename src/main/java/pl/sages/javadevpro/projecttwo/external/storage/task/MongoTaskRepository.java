package pl.sages.javadevpro.projecttwo.external.storage.task;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoTaskRepository extends MongoRepository<TaskEntity,String> {
}
