package pl.sages.javadevpro.projecttwo.domain.quiz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizSolutionTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizSolutionTemplateAlreadyExistsException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class QuizSolutionTemplateServiceTest {

    @Mock
    private QuizSolutionTemplateRepository quizSolutionTemplateRepository;

    @InjectMocks
    private QuizSolutionTemplateService quizSolutionTemplateService;

    private final QuizSolutionTemplate fakeQuizSolutionTemplate = new QuizSolutionTemplate(
            "ID123",
            "TID123",
            500,
            100,
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
                                    "Single choice answer 20",
                                    "Single choice answer 30",
                                    "Single choice answer 40"
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

    @Test
    void save_method_should_return_saved_quiz_solution_template_when_quiz_solution_template_does_not_exist() {
        Mockito.when(quizSolutionTemplateRepository.save(fakeQuizSolutionTemplate)).thenReturn(fakeQuizSolutionTemplate);

        //when
        QuizSolutionTemplate savedQuizSolutionTemplate = quizSolutionTemplateService.save(fakeQuizSolutionTemplate);

        //then
        compareQuizSolutionTemplates(fakeQuizSolutionTemplate, savedQuizSolutionTemplate);
    }

    @Test
    void save_method_should_throw_quiz_solution_template_already_exist_exception_when_quiz_solution_template_exist() {
        Mockito.when(quizSolutionTemplateRepository.save(fakeQuizSolutionTemplate)).thenThrow(new QuizSolutionTemplateAlreadyExistsException());

        //when
        //then
        Assertions.assertThrows(QuizSolutionTemplateAlreadyExistsException.class,
                ()-> quizSolutionTemplateService.save(fakeQuizSolutionTemplate));
    }

    @Test
    void find_by_id_method_should_return_founded_quiz_solution_template_when_quiz_solution_template_exist() {
        Mockito.when(quizSolutionTemplateRepository.findById(fakeQuizSolutionTemplate.getTestSolutionId())).thenReturn(Optional.of(fakeQuizSolutionTemplate));

        //when
        QuizSolutionTemplate foundedQuizSolutionTemplate = quizSolutionTemplateService.findById(fakeQuizSolutionTemplate.getTestSolutionId());

        //then
        compareQuizSolutionTemplates(fakeQuizSolutionTemplate, foundedQuizSolutionTemplate);
    }

    @Test
    void find_by_id_method_should_throw_quiz_solution_template_not_found_exception_when_quiz_solution_template_does_not_exist() {
        Mockito.when(quizSolutionTemplateRepository.findById(fakeQuizSolutionTemplate.getTestSolutionId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(QuizSolutionTemplateNotFoundException.class,
                ()-> quizSolutionTemplateService.findById(fakeQuizSolutionTemplate.getTestSolutionId()));
    }

    @Test
    void find_by_quiz_template_id_method_should_return_founded_quiz_solution_template_when_quiz_solution_template_exist() {
        Mockito.when(quizSolutionTemplateRepository.findByQuizTemplateId(fakeQuizSolutionTemplate.getTestTemplateId())).thenReturn(Optional.of(fakeQuizSolutionTemplate));

        //when
        QuizSolutionTemplate foundedQuizSolutionTemplate = quizSolutionTemplateService.findByQuizTemplateId(fakeQuizSolutionTemplate.getTestTemplateId());

        //then
        compareQuizSolutionTemplates(fakeQuizSolutionTemplate, foundedQuizSolutionTemplate);
    }

    @Test
    void find_by_quiz_template__id_method_should_throw_quiz_solution_template_not_found_exception_when_quiz_solution_template_does_not_exist() {
        Mockito.when(quizSolutionTemplateRepository.findByQuizTemplateId(fakeQuizSolutionTemplate.getTestTemplateId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(QuizSolutionTemplateNotFoundException.class,
                ()-> quizSolutionTemplateService.findByQuizTemplateId(fakeQuizSolutionTemplate.getTestTemplateId()));
    }

    @Test
    void remove_method_should_invoke_remove_method_from_quiz_solution_template_repository() {
        //when
        quizSolutionTemplateService.removeById(fakeQuizSolutionTemplate.getTestTemplateId());
        //then
        Mockito.verify(quizSolutionTemplateRepository).remove(fakeQuizSolutionTemplate.getTestTemplateId());
    }

    @Test
    void update_method_should_invoke_update_method_from_quiz_solution_template_repository() {
        //when
        quizSolutionTemplateService.update(fakeQuizSolutionTemplate);
        //then
        Mockito.verify(quizSolutionTemplateRepository).update(fakeQuizSolutionTemplate);
    }

    @Test
    void find_all_method_should_invoke_find_all_method_from_quiz_solution_template_repository() {
        //when
        quizSolutionTemplateService.findAll(null);
        //then
        Mockito.verify(quizSolutionTemplateRepository).findAll(null);
    }

    private void compareQuizSolutionTemplates(QuizSolutionTemplate model, QuizSolutionTemplate tested) {
        assertNotNull(tested);
        assertEquals(model.getTestTemplateId(), tested.getTestTemplateId());
        assertEquals(model.getTestSolutionId(), tested.getTestSolutionId());
        assertEquals(model.getMaximumScore(), tested.getMaximumScore());
        assertEquals(model.getScore(), tested.getScore());
        assertEquals(model.getGrade(), tested.getGrade());
        assertEquals(model.getTestName(), tested.getTestName());
        Assertions.assertIterableEquals(model.getQuestions(), tested.getQuestions());
    }
}