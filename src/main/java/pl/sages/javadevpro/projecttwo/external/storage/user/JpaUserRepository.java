package pl.sages.javadevpro.projecttwo.external.storage.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends CrudRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

}
