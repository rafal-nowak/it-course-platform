package pl.sages.javadevpro.projecttwo.domain.quiz;

import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    Quiz save(Quiz quiz);
    void update(Quiz quiz);
    void remove(String id);
    Optional<Quiz> findById(String id);
    PageQuiz findAll(Pageable pageable);
    PageQuiz findAllByUserId(Pageable pageable, String userId);
    List<Quiz> findAllByTestTemplateIdAndUserId(String testTemplateId, String userId);

}
