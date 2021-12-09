package pl.sages.javadevpro.projecttwo.external.storage;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaUserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);
}
