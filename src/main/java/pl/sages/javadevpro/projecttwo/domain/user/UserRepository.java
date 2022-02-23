package pl.sages.javadevpro.projecttwo.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> update(User user);

    Optional<User> remove(String id);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    List<User> findAll();

}
