package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoQuizSolutionTemplateRepository extends MongoRepository<QuizSolutionTemplateEntity, String> {
    Optional<QuizSolutionTemplateEntity> findByTestTemplateId(String id);
}
