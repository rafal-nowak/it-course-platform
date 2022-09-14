package pl.sages.javadevpro.projecttwo.domain.gradingtable;

import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.PageGradingTable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;

import java.util.Optional;

public interface GradingTableRepository {
    GradingTable save(GradingTable gradingTable);
    void update(GradingTable gradingTable);
    void remove(String id);
    Optional<GradingTable> findById(String id);
    PageGradingTable findAll(Pageable pageable);
}
