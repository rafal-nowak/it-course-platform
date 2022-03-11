package pl.sages.javadevpro.projecttwo.domain.assigment;

import java.util.Optional;

public interface AssigmentRepository {

    Assigment save(Assigment assigment);

    Optional<Assigment> find(String userId, String taskId);

}
