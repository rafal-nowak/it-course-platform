package pl.sages.javadevpro.projecttwo.domain.assigment;

public interface AssigmentRepository {

    //    Optional<Assigment> find(String userId, String taskId); TODO

    Assigment save(Assigment assigment);

}
