package pl.sages.javadevpro.projecttwo.external.storage;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryRepository {

    private List<UserEntity> users = new CopyOnWriteArrayList<UserEntity>();

    public UserEntity save(UserEntity entity) {
        users.add(entity);
        return entity;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }
}
