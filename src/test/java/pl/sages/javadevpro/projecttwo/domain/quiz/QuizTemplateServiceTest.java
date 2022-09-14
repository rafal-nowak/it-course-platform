package pl.sages.javadevpro.projecttwo.domain.quiz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizTemplateNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintNotFoundException;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizTemplateAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizTemplateServiceTest {

    @Mock
    private QuizTemplateRepository quizTemplateRepository;

    @InjectMocks
    private QuizTemplateService quizTemplateService;

    private final QuizTemplate fakeQuizTemplate = new QuizTemplate(
            "ID123",
            "GTID123",
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
    void save_method_should_return_saved_quiz_template_when_quiz_template_does_not_exist() {
        Mockito.when(quizTemplateRepository.save(fakeQuizTemplate)).thenReturn(fakeQuizTemplate);

        //when
        QuizTemplate savedQuizTemplate = quizTemplateService.save(fakeQuizTemplate);

        //then
        compareQuizTemplates(fakeQuizTemplate, savedQuizTemplate);
    }

    @Test
    void save_method_should_throw_quiz_template_already_exist_exception_when_quiz_template_exist() {
        Mockito.when(quizTemplateRepository.save(fakeQuizTemplate)).thenThrow(new QuizTemplateAlreadyExistsException());

        //when
        //then
        Assertions.assertThrows(QuizTemplateAlreadyExistsException.class,
                ()-> quizTemplateService.save(fakeQuizTemplate));
    }

    @Test
    void find_by_id_method_should_return_founded_quiz_template_when_quiz_template_exist() {
        Mockito.when(quizTemplateRepository.findById(fakeQuizTemplate.getTestTemplateId())).thenReturn(Optional.of(fakeQuizTemplate));

        //when
        QuizTemplate foundedQuizTemplate = quizTemplateService.findById(fakeQuizTemplate.getTestTemplateId());

        //then
        compareQuizTemplates(fakeQuizTemplate, foundedQuizTemplate);
    }

    @Test
    void find_by_id_method_should_throw_quiz_template_not_found_exception_when_quiz_template_does_not_exist() {
        Mockito.when(quizTemplateRepository.findById(fakeQuizTemplate.getTestTemplateId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(QuizTemplateNotFoundException.class,
                ()-> quizTemplateService.findById(fakeQuizTemplate.getTestTemplateId()));
    }

    @Test
    void remove_method_should_invoke_remove_method_from_quiz_template_repository() {
        //when
        quizTemplateService.removeById(fakeQuizTemplate.getTestTemplateId());
        //then
        Mockito.verify(quizTemplateRepository).remove(fakeQuizTemplate.getTestTemplateId());
    }

    @Test
    void update_method_should_invoke_update_method_from_quiz_template_repository() {
        //when
        quizTemplateService.update(fakeQuizTemplate);
        //then
        Mockito.verify(quizTemplateRepository).update(fakeQuizTemplate);
    }

    @Test
    void find_all_method_should_invoke_find_all_method_from_quiz_template_repository() {
        //when
        quizTemplateService.findAll(null);
        //then
        Mockito.verify(quizTemplateRepository).findAll(null);
    }

    private void compareQuizTemplates(QuizTemplate model, QuizTemplate tested) {
        assertNotNull(tested);
        assertEquals(model.getTestTemplateId(), tested.getTestTemplateId());
        assertEquals(model.getGradingTableId(), tested.getGradingTableId());
        assertEquals(model.getMaximumScore(), tested.getMaximumScore());
        assertEquals(model.getScore(), tested.getScore());
        assertEquals(model.getGrade(), tested.getGrade());
        assertEquals(model.getTestName(), tested.getTestName());
        Assertions.assertIterableEquals(model.getQuestions(), tested.getQuestions());
    }

}