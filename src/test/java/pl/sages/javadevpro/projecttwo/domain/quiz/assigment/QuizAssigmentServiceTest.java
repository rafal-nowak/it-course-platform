package pl.sages.javadevpro.projecttwo.domain.quiz.assigment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.GradingTableEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.MongoGradingTableRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizAssigmentServiceTest {

    @Mock
    private QuizTemplateRepository quizTemplateRepository;
    @Mock
    private QuizRepository quizRepository;
    @InjectMocks
    private QuizAssigmentService quizAssigmentService;

    String fakeUserId = "UID123";
    QuizTemplate fakeQuizTemplate = new QuizTemplate(
            "ID123",
            "GT123",
            500,
            300,
            "Excellent",
            "Test Name",
            List.of(
                    new Question(
                            1L,
                            4,
                            0,
                            QuestionType.SINGLE_CHOICE_CLOSED_QUESTION,
                            "Single choice question?",
                            List.of(
                                    "Single choice answer 20"
                            )
                    ),
                    new Question(
                            2L,
                            4,
                            0,
                            QuestionType.MULTI_CHOICE_CLOSED_QUESTION,
                            "Single choice question?",
                            List.of(
                                    "Multi choice answer 20",
                                    "Multi choice answer 30",
                                    "Multi choice answer 40"
                            )
                    )
            )
    );

    Quiz fakeQuiz = new Quiz(
            null,
            fakeQuizTemplate.getGradingTableId(),
            fakeUserId,
            fakeQuizTemplate.getTestTemplateId(),
            fakeQuizTemplate.getMaximumScore(),
            fakeQuizTemplate.getScore(),
            fakeQuizTemplate.getGrade(),
            fakeQuizTemplate.getTestName(),
            QuizStatus.ASSIGNED,
            fakeQuizTemplate.getQuestions().stream().collect(Collectors.toList())
    );

    @Test
    void assign_new_quiz_method_should_invoke_save_method_with_new_created_quiz_from_quiz_repository() {

        //when
        Mockito.when(quizTemplateRepository.findById(fakeQuizTemplate.getTestTemplateId())).thenReturn(Optional.of(fakeQuizTemplate));

        quizAssigmentService.assignNewQuiz(fakeUserId, fakeQuizTemplate.getTestTemplateId());
        //then
        Mockito.verify(quizRepository).save(fakeQuiz);
    }
}