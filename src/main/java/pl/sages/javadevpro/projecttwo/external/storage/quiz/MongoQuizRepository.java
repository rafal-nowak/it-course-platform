package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoQuizRepository extends MongoRepository<QuizEntity, String> {
    Page<QuizEntity> findAllByUserId(Pageable pageable, String userId);

    List<QuizEntity> findAllByTestTemplateIdAndUserId(String testTemplateId, String userId);
}
