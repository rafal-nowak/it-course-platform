package pl.sages.javadevpro.projecttwo.domain.assigment;

import java.util.Optional;

public interface AssigmentRepository {

//    Optional<Assigment> find(String userId, String taskId); TODO

    Assigment save(Assigment assigment);

}
