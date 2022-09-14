package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.QuizSolutionTemplateFactory;
import pl.sages.javadevpro.projecttwo.TestUserFactory;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.PageQuizSolutionTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.QuizSolutionTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.api.task.blueprint.TaskBlueprintDto;
import pl.sages.javadevpro.projecttwo.api.usertask.ErrorResponse;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizSolutionTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuizSolutionTemplateControllerIT extends BaseIT {
    @Autowired
    private QuizSolutionTemplateService quizSolutionTemplateService;
    @Autowired
    private QuizSolutionTemplateDtoMapper quizSolutionTemplateMapper;
    @Autowired
    private PageQuizSolutionTemplateDtoMapper pagequizSolutionTemplateDtoMapper;
    @Autowired
    UserService userService;

    @Test
    void admin_should_be_able_to_get_information_about_quiz_solution_template() {
        //given
        String adminAccessToken = getTokenForAdmin();
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        quizSolutionTemplateService.save(quizSolutionTemplate);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-solution-templates/" + quizSolutionTemplate.getTestSolutionId(),
                adminAccessToken,
                null,
                QuizSolutionTemplateDto.class);

        //then
        QuizSolutionTemplateDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        comparequizSolutionTemplates(quizSolutionTemplate, quizSolutionTemplateMapper.toDomain(body));
    }

    @Test
    void student_should_not_be_able_to_get_information_about_quiz_solution_template() {
        //given
        User user = TestUserFactory.createStudent();
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        userService.save(user);
        quizSolutionTemplateService.save(quizSolutionTemplate);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-solution-templates/" + quizSolutionTemplate.getTestSolutionId(),
                token,
                null,
                QuizSolutionTemplateDto.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_get_information_about_correct_quiz_solution_template() {
        //given
        String adminAccessToken = getTokenForAdmin();
        QuizSolutionTemplate quizSolutionTemplate1 = QuizSolutionTemplateFactory.createRandom();
        QuizSolutionTemplate quizSolutionTemplate2 = QuizSolutionTemplateFactory.createRandom();
        QuizSolutionTemplate quizSolutionTemplate3 = QuizSolutionTemplateFactory.createRandom();
        quizSolutionTemplateService.save(quizSolutionTemplate1);
        quizSolutionTemplateService.save(quizSolutionTemplate2);
        quizSolutionTemplateService.save(quizSolutionTemplate3);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-solution-templates/" + quizSolutionTemplate2.getTestSolutionId(),
                adminAccessToken,
                null,
                QuizSolutionTemplateDto.class);

        //then
        QuizSolutionTemplateDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        comparequizSolutionTemplates(quizSolutionTemplate2, quizSolutionTemplateMapper.toDomain(body));
    }

    @Test
    void admin_should_be_able_to_get_information_about_all_quiz_solution_templates() {
        //given
        String adminAccessToken = getTokenForAdmin();
        QuizSolutionTemplate quizSolutionTemplate1 = QuizSolutionTemplateFactory.createRandom();
        QuizSolutionTemplate quizSolutionTemplate2 = QuizSolutionTemplateFactory.createRandom();
        QuizSolutionTemplate quizSolutionTemplate3 = QuizSolutionTemplateFactory.createRandom();
        quizSolutionTemplateService.save(quizSolutionTemplate1);
        quizSolutionTemplateService.save(quizSolutionTemplate2);
        quizSolutionTemplateService.save(quizSolutionTemplate3);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-solution-templates",
                adminAccessToken,
                null,
                PageQuizSolutionTemplateDto.class);

        //then
        PageQuizSolutionTemplateDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        comparequizSolutionTemplates(quizSolutionTemplate1, quizSolutionTemplateMapper.toDomain(body.getQuizSolutionTemplates().get(0)));
        comparequizSolutionTemplates(quizSolutionTemplate2, quizSolutionTemplateMapper.toDomain(body.getQuizSolutionTemplates().get(1)));
        comparequizSolutionTemplates(quizSolutionTemplate3, quizSolutionTemplateMapper.toDomain(body.getQuizSolutionTemplates().get(2)));
    }

    @Test
    void admin_should_be_able_to_save_new_quiz_solution_template() {
        //given
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-solution-templates",
                adminAccessToken,
                quizSolutionTemplateMapper.toDto(quizSolutionTemplate),
                QuizSolutionTemplateDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        QuizSolutionTemplateDto body = response.getBody();
        //and
        comparequizSolutionTemplates(quizSolutionTemplate, quizSolutionTemplateMapper.toDomain(body));
    }

    @Test
    void student_should_not_be_able_to_save_new_quiz_solution_template() {
        //given
        User user = TestUserFactory.createStudent();
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-solution-templates",
                token,
                quizSolutionTemplateMapper.toDto(quizSolutionTemplate),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void should_return_conflict_about_duplicated_quiz_solution_template(){
        //given
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        quizSolutionTemplateService.save(quizSolutionTemplate);
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-solution-templates",
                adminAccessToken,
                quizSolutionTemplateMapper.toDto(quizSolutionTemplate),
                ErrorResponse.class);
        //then
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_quiz_solution_template() {
        //given
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        quizSolutionTemplateService.save(quizSolutionTemplate);
        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/quiz-solution-templates/" + quizSolutionTemplate.getTestSolutionId(),
                adminAccessToken,
                null,
                Void.class);
        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void student_should_not_be_able_to_delete_quiz_solution_template() {
        //given
        User user = TestUserFactory.createStudent();
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        quizSolutionTemplateService.save(quizSolutionTemplate);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/quiz-solution-templates/" + quizSolutionTemplate.getTestSolutionId(),
                token,
                null,
                ErrorResponse.class
        );

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_update_quiz_solution_template() {
        //given
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        QuizSolutionTemplate updatedQuizSolutionTemplate = new QuizSolutionTemplate(
                quizSolutionTemplate.getTestSolutionId(),
                quizSolutionTemplate.getTestTemplateId(),
                600,
                400,
                "Grade is updated",
                "Table Name is updated",
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
        String adminAccessToken = getTokenForAdmin();
        quizSolutionTemplateService.save(quizSolutionTemplate);

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/quiz-solution-templates",
                adminAccessToken,
                quizSolutionTemplateMapper.toDto(updatedQuizSolutionTemplate),
                TaskBlueprintDto.class);

        QuizSolutionTemplate quizSolutionTemplateFromDb = quizSolutionTemplateService.findByQuizTemplateId(quizSolutionTemplate.getTestTemplateId());

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        TaskBlueprintDto body = response.getBody();
        Assertions.assertNull(body);
        comparequizSolutionTemplates(updatedQuizSolutionTemplate, quizSolutionTemplateFromDb);
    }

    @Test
    void should_get_response_code_404_when_quiz_solution_template_not_exits() {
        //given
        String adminAccessToken = getTokenForAdmin();
        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-solution-templates/1",
                adminAccessToken,
                null,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private void comparequizSolutionTemplates(QuizSolutionTemplate model, QuizSolutionTemplate tested) {
        assertNotNull(tested);
        assertEquals(model.getTestSolutionId(), tested.getTestSolutionId());
        assertEquals(model.getTestTemplateId(), tested.getTestTemplateId());
        assertEquals(model.getTestName(), tested.getTestName());
        assertEquals(model.getMaximumScore(), tested.getMaximumScore());
        assertEquals(model.getScore(), tested.getScore());
        assertEquals(model.getGrade(), tested.getGrade());
        Assertions.assertIterableEquals(model.getQuestions(), tested.getQuestions());
    }
}
