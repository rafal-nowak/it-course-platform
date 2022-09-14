package pl.sages.javadevpro.projecttwo.domain.quiz.assigment;

import lombok.RequiredArgsConstructor;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class QuizAssigmentService {

    private final QuizTemplateRepository quizTemplateRepository;
    private final QuizRepository quizRepository;

    public void assignNewQuiz(String userId, String quizTemplateId) {
        QuizTemplate quizTemplate = quizTemplateRepository.findById(quizTemplateId)
                .orElseThrow(QuizTemplateNotFoundException::new);

        Quiz quiz = new Quiz(
                null,
                quizTemplate.getGradingTableId(),
                userId,
                quizTemplate.getTestTemplateId(),
                quizTemplate.getMaximumScore(),
                quizTemplate.getScore(),
                quizTemplate.getGrade(),
                quizTemplate.getTestName(),
                QuizStatus.ASSIGNED,
                quizTemplate.getQuestions().stream().collect(Collectors.toList())
        );

        quizRepository.save(quiz);
    }
}
