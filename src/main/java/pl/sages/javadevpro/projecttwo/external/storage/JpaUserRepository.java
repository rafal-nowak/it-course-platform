package pl.sages.javadevpro.projecttwo.external.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);
}
