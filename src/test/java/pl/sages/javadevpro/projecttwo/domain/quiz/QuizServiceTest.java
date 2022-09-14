package pl.sages.javadevpro.projecttwo.domain.quiz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableService;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingRecord;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizNotCheckedException;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizAlreadyExistsException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizSolutionTemplateService quizSolutionTemplateService;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private GradingTableService gradingTableService;
    @InjectMocks
    private QuizService quizService;

    private final Quiz fakeQuiz = new Quiz(
            "ID123",
            "GTID123",
            "UID123",
            "TTID123",
            500,
            300,
            "Excellent",
            "test name",
            QuizStatus.ASSIGNED,
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

    private final Quiz fakeQuizWithOpenQuestion = new Quiz(
            "ID123",
            "GTID123",
            "UID123",
            "TTID123",
            500,
            300,
            "Excellent",
            "test name",
            QuizStatus.ASSIGNED,
            List.of(
                    new Question(
                            1L,
                            4,
                            0,
                            QuestionType.SINGLE_CHOICE_CLOSED_QUESTION,
                            "Single choice question?",
                            List.of(
                                    "Single choice answer 30"
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
                                    "Multi choice answer 30"
                            )
                    ),
                    new Question(
                            3L,
                            4,
                            0,
                            QuestionType.OPEN_QUESTION,
                            "Open question?",
                            List.of(

                            )
                    )
            )
    );

    private final QuizSolutionTemplate fakeQuizSolutionTemplate = new QuizSolutionTemplate(
            "ID234",
            "TTID123",
            500,
            300,
            "Excellent",
            "test name",
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

    private final GradingTable fakeGradingTable = new GradingTable(
            "GDID123",
            "table name",
            List.of(
                    new GradingRecord(
                            0,
                            50,
                            "1"
                    ),
                    new GradingRecord(
                            51,
                            60,
                            "2"
                    ),
                    new GradingRecord(
                            61,
                            70,
                            "3"
                    ),
                    new GradingRecord(
                            71,
                            80,
                            "4"
                    ),
                    new GradingRecord(
                            81,
                            90,
                            "5"
                    ),
                    new GradingRecord(
                            91,
                            100,
                            "6"
                    )
            )
    );

    @Test
    void save_method_should_return_saved_quiz_when_quiz_does_not_exist() {
        Mockito.when(quizRepository.save(fakeQuiz)).thenReturn(fakeQuiz);

        //when
        Quiz savedQuiz = quizService.save(fakeQuiz);

        //then
        compareQuizzes(fakeQuiz, savedQuiz);
    }

    @Test
    void save_method_should_throw_quiz_already_exist_exception_when_quiz_exist() {
        Mockito.when(quizRepository.save(fakeQuiz)).thenThrow(new QuizAlreadyExistsException());

        //when
        //then
        Assertions.assertThrows(QuizAlreadyExistsException.class,
                ()-> quizService.save(fakeQuiz));
    }

    @Test
    void find_by_id_method_should_return_founded_quiz_when_quiz_exist() {
        Mockito.when(quizRepository.findById(fakeQuiz.getTestId())).thenReturn(Optional.of(fakeQuiz));

        //when
        Quiz foundedQuiz = quizService.findById(fakeQuiz.getTestId());

        //then
        compareQuizzes(fakeQuiz, foundedQuiz);
    }

    @Test
    void find_by_id_method_should_throw_quiz_not_found_exception_when_quiz_does_not_exist() {
        Mockito.when(quizRepository.findById(fakeQuiz.getTestId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(QuizNotFoundException.class,
                ()-> quizService.findById(fakeQuiz.getTestId()));
    }

    @Test
    void remove_method_should_invoke_remove_method_from_quiz_repository() {
        //when
        quizService.removeById(fakeQuiz.getTestId());
        //then
        Mockito.verify(quizRepository).remove(fakeQuiz.getTestId());
    }

    @Test
    void update_method_should_invoke_update_method_from_quiz_repository() {
        //when
        quizService.update(fakeQuiz);
        //then
        Mockito.verify(quizRepository).update(fakeQuiz);
    }

    @Test
    void find_all_method_should_invoke_find_all_method_from_quiz_repository() {
        //when
        quizService.findAll(null);
        //then
        Mockito.verify(quizRepository).findAll(null);
    }

    @Test
    void find_all_by_user_id_method_should_invoke_find_all_by_user_id_method_from_quiz_repository() {
        //when
        quizService.findAllByUserId(null, "123");
        //then
        Mockito.verify(quizRepository).findAllByUserId(null, "123");
    }

    @Test
    void execute_command_method_should_ensure_that_the_test_is_checked_when_invoked_with_the_check_argument_for_test_without_open_question() {
        //given
        Mockito.when(quizRepository.findById(fakeQuiz.getTestId())).thenReturn(Optional.of(fakeQuiz));
        Mockito.when(quizSolutionTemplateService.findByQuizTemplateId(fakeQuiz.getTestTemplateId())).thenReturn(fakeQuizSolutionTemplate);
        //when
        quizService.executeCommand(QuizCommand.CHECK, fakeQuiz.getTestId());
        //then
        assertEquals(fakeQuiz.getStatus(), QuizStatus.CHECKED);
    }

    @Test
    void execute_command_method_should_ensure_that_the_test_is_partially_checked_when_invoked_with_the_check_argument_for_test_with_open_question() {
        //given
        Mockito.when(quizRepository.findById(fakeQuizWithOpenQuestion.getTestId())).thenReturn(Optional.of(fakeQuizWithOpenQuestion));
        Mockito.when(quizSolutionTemplateService.findByQuizTemplateId(fakeQuizWithOpenQuestion.getTestTemplateId())).thenReturn(fakeQuizSolutionTemplate);
        //when
        quizService.executeCommand(QuizCommand.CHECK, fakeQuizWithOpenQuestion.getTestId());
        //then
        assertEquals(fakeQuizWithOpenQuestion.getStatus(), QuizStatus.PARTIALLY_CHECKED);
    }

    @Test
    void execute_command_method_should_ensure_that_the_test_is_rated_when_invoked_with_the_rate_argument_for_checked_test() {
        //given
        Mockito.when(quizRepository.findById(fakeQuiz.getTestId())).thenReturn(Optional.of(fakeQuiz));
        Mockito.when(quizSolutionTemplateService.findByQuizTemplateId(fakeQuizWithOpenQuestion.getTestTemplateId())).thenReturn(fakeQuizSolutionTemplate);
        Mockito.when(gradingTableService.findById(fakeQuiz.getGradingTableId())).thenReturn(fakeGradingTable);
        //when
        quizService.executeCommand(QuizCommand.CHECK, fakeQuiz.getTestId());
        quizService.executeCommand(QuizCommand.RATE, fakeQuiz.getTestId());
        //then
        assertEquals(fakeQuiz.getStatus(), QuizStatus.RATED);
    }

    @Test
    void execute_command_method_should_throw_quiz_not_checked_exception_for_unchecked_test() {
        //given
        Mockito.when(quizRepository.findById(fakeQuiz.getTestId())).thenReturn(Optional.of(fakeQuiz));
        Mockito.when(gradingTableService.findById(fakeQuiz.getGradingTableId())).thenReturn(fakeGradingTable);
        fakeQuiz.setStatus(QuizStatus.ASSIGNED);
        //when
        //then
        Assertions.assertThrows(QuizNotCheckedException.class,
                ()-> quizService.executeCommand(QuizCommand.RATE, fakeQuiz.getTestId()));
    }

    private void compareQuizzes(Quiz model, Quiz tested) {
        assertNotNull(tested);
        assertEquals(model.getTestId(), tested.getTestId());
        assertEquals(model.getTestTemplateId(), tested.getTestTemplateId());
        assertEquals(model.getGradingTableId(), tested.getGradingTableId());
        assertEquals(model.getUserId(), tested.getUserId());
        assertEquals(model.getMaximumScore(), tested.getMaximumScore());
        assertEquals(model.getScore(), tested.getScore());
        assertEquals(model.getGrade(), tested.getGrade());
        assertEquals(model.getTestName(), tested.getTestName());
        Assertions.assertIterableEquals(model.getQuestions(), tested.getQuestions());
    }

}