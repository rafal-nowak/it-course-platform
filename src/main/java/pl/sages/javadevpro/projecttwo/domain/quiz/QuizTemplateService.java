package pl.sages.javadevpro.projecttwo.domain.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@RequiredArgsConstructor
public class QuizTemplateService {

    private final QuizTemplateRepository quizTemplateRepository;

    public QuizTemplate save(QuizTemplate quizTemplate) {
        return quizTemplateRepository.save(quizTemplate);
    }

    public void update(QuizTemplate quizTemplate) {
        quizTemplateRepository.update(quizTemplate);
    }

    public void removeById(String id) {
        quizTemplateRepository.remove(id);
    }

    public QuizTemplate findById(String id) {
        return quizTemplateRepository.findById(id)
                .orElseThrow(QuizTemplateNotFoundException::new);
    }

    public PageQuizTemplate findAll(Pageable pageable) {
        return quizTemplateRepository.findAll(pageable);
    }
}
