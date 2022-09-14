package pl.sages.javadevpro.projecttwo.external.storage.gradingtable;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizEntity;

public interface MongoGradingTableRepository extends MongoRepository<GradingTableEntity, String> {
}
