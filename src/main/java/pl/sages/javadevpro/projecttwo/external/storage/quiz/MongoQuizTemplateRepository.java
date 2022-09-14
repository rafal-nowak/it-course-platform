package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoQuizTemplateRepository extends MongoRepository<QuizTemplateEntity, String> {
}
