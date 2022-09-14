package pl.sages.javadevpro.projecttwo.domain.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizSolutionTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@RequiredArgsConstructor
public class QuizSolutionTemplateService {

    private final QuizSolutionTemplateRepository quizSolutionTemplateRepository;

    public QuizSolutionTemplate save(QuizSolutionTemplate quizSolutionTemplate) {
        return quizSolutionTemplateRepository.save(quizSolutionTemplate);
    }

    public void update(QuizSolutionTemplate quizSolutionTemplate) {
        quizSolutionTemplateRepository.update(quizSolutionTemplate);
    }

    public void removeById(String id) {
        quizSolutionTemplateRepository.remove(id);
    }

    public QuizSolutionTemplate findById(String id) {
        return quizSolutionTemplateRepository.findById(id)
                .orElseThrow(QuizSolutionTemplateNotFoundException::new);
    }

    public QuizSolutionTemplate findByQuizTemplateId(String id) {
        return quizSolutionTemplateRepository.findByQuizTemplateId(id)
                .orElseThrow(QuizSolutionTemplateNotFoundException::new);
    }

    public PageQuizSolutionTemplate findAll(Pageable pageable) {
        return quizSolutionTemplateRepository.findAll(pageable);
    }
}
