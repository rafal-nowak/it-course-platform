package pl.sages.javadevpro.projecttwo.domain.quiz;

import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.Optional;

public interface QuizSolutionTemplateRepository {
    QuizSolutionTemplate save(QuizSolutionTemplate quizSolutionTemplate);
    void update(QuizSolutionTemplate quizSolutionTemplate);
    void remove(String id);
    Optional<QuizSolutionTemplate> findById(String id);
    PageQuizSolutionTemplate findAll(Pageable pageable);
    Optional<QuizSolutionTemplate> findByQuizTemplateId(String id);

}
