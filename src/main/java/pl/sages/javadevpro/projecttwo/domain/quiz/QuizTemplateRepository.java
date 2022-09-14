package pl.sages.javadevpro.projecttwo.domain.quiz;

import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.Optional;

public interface QuizTemplateRepository {
    QuizTemplate save(QuizTemplate quizTemplate);
    void update(QuizTemplate quizTemplate);
    void remove(String id);
    Optional<QuizTemplate> findById(String id);
    PageQuizTemplate findAll(Pageable pageable);
}
