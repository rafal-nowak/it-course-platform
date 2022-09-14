package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.QuizFactory;
import pl.sages.javadevpro.projecttwo.QuizTemplateFactory;
import pl.sages.javadevpro.projecttwo.TestUserFactory;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.PageQuizTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.QuizTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.api.task.blueprint.TaskBlueprintDto;
import pl.sages.javadevpro.projecttwo.api.usertask.ErrorResponse;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizService;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuizTemplateControllerIT extends BaseIT {

    @Autowired
    private QuizTemplateService quizTemplateService;
    @Autowired
    private QuizTemplateDtoMapper quizTemplateMapper;
    @Autowired
    private PageQuizTemplateDtoMapper pageQuizTemplateDtoMapper;
    @Autowired
    UserService userService;
    @Autowired
    QuizService quizService;

    @Test
    void admin_should_be_able_to_get_information_about_quiz_template() {
        //given
        String adminAccessToken = getTokenForAdmin();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        quizTemplateService.save(quizTemplate);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-templates/" + quizTemplate.getTestTemplateId(),
                adminAccessToken,
                null,
                QuizTemplateDto.class);

        //then
        QuizTemplateDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareQuizTemplates(quizTemplate, quizTemplateMapper.toDomain(body));
    }

    @Test
    void student_should_be_able_to_get_information_about_quiz_template_if_has_quiz_created_from_this_quiz_template() {
        //given
        User user = TestUserFactory.createStudent();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        Quiz quiz = QuizFactory.createRandom();
        quiz.setTestTemplateId(quizTemplate.getTestTemplateId());
        quiz.setUserId(user.getId());
        quizTemplateService.save(quizTemplate);
        quizService.save(quiz);
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-templates/" + quizTemplate.getTestTemplateId(),
                token,
                null,
                QuizTemplateDto.class);

        //then
        QuizTemplateDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareQuizTemplates(quizTemplate, quizTemplateMapper.toDomain(body));
    }

    @Test
    void student_should_not_be_able_to_get_information_about_quiz_template_if_has_not_quiz_created_from_this_quiz_template() {
        //given
        User user = TestUserFactory.createStudent();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        userService.save(user);
        quizTemplateService.save(quizTemplate);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-templates/" + quizTemplate.getTestTemplateId(),
                token,
                null,
                QuizTemplateDto.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_get_information_about_correct_quiz_template() {
        //given
        String adminAccessToken = getTokenForAdmin();
        QuizTemplate quizTemplate1 = QuizTemplateFactory.createRandom();
        QuizTemplate quizTemplate2 = QuizTemplateFactory.createRandom();
        QuizTemplate quizTemplate3 = QuizTemplateFactory.createRandom();
        quizTemplateService.save(quizTemplate1);
        quizTemplateService.save(quizTemplate2);
        quizTemplateService.save(quizTemplate3);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-templates/" + quizTemplate2.getTestTemplateId(),
                adminAccessToken,
                null,
                QuizTemplateDto.class);

        //then
        QuizTemplateDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareQuizTemplates(quizTemplate2, quizTemplateMapper.toDomain(body));
    }

    @Test
    void admin_should_be_able_to_get_information_about_all_quiz_templates() {
        //given
        String adminAccessToken = getTokenForAdmin();
        QuizTemplate quizTemplate1 = QuizTemplateFactory.createRandom();
        QuizTemplate quizTemplate2 = QuizTemplateFactory.createRandom();
        QuizTemplate quizTemplate3 = QuizTemplateFactory.createRandom();
        quizTemplateService.save(quizTemplate1);
        quizTemplateService.save(quizTemplate2);
        quizTemplateService.save(quizTemplate3);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-templates",
                adminAccessToken,
                null,
                PageQuizTemplateDto.class);

        //then
        PageQuizTemplateDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        compareQuizTemplates(quizTemplate1, quizTemplateMapper.toDomain(body.getQuizTemplates().get(0)));
        compareQuizTemplates(quizTemplate2, quizTemplateMapper.toDomain(body.getQuizTemplates().get(1)));
        compareQuizTemplates(quizTemplate3, quizTemplateMapper.toDomain(body.getQuizTemplates().get(2)));
    }

    @Test
    void admin_should_be_able_to_save_new_quiz_template() {
        //given
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-templates",
                adminAccessToken,
                quizTemplateMapper.toDto(quizTemplate),
                QuizTemplateDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        QuizTemplateDto body = response.getBody();
        //and
        compareQuizTemplates(quizTemplate, quizTemplateMapper.toDomain(body));
    }

    @Test
    void student_should_not_be_able_to_save_new_quiz_template() {
        //given
        User user = TestUserFactory.createStudent();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-templates",
                token,
                quizTemplateMapper.toDto(quizTemplate),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void should_return_conflict_about_duplicated_quiz_template(){
        //given
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        quizTemplateService.save(quizTemplate);
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-templates",
                adminAccessToken,
                quizTemplateMapper.toDto(quizTemplate),
                ErrorResponse.class);
        //then
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_quiz_template() {
        //given
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        quizTemplateService.save(quizTemplate);
        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/quiz-templates/" + quizTemplate.getTestTemplateId(),
                adminAccessToken,
                null,
                Void.class);
        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void student_should_not_be_able_to_delete_quiz_template() {
        //given
        User user = TestUserFactory.createStudent();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        quizTemplateService.save(quizTemplate);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/quiz-templates/" + quizTemplate.getTestTemplateId(),
                token,
                null,
                ErrorResponse.class
        );

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_update_quiz_template() {
        //given
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        QuizTemplate updatedQuizTemplate = new QuizTemplate(
                quizTemplate.getTestTemplateId(),
                "Grading table id is updated",
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
        quizTemplateService.save(quizTemplate);

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/quiz-templates",
                adminAccessToken,
                quizTemplateMapper.toDto(updatedQuizTemplate),
                QuizTemplateDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        QuizTemplateDto body = response.getBody();
        Assertions.assertNull(body);
    }

    @Test
    void should_get_response_code_404_when_quiz_template_not_exits() {
        //given
        String adminAccessToken = getTokenForAdmin();
        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quiz-templates/1",
                adminAccessToken,
                null,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private void compareQuizTemplates(QuizTemplate model, QuizTemplate tested) {
        assertNotNull(tested);
        assertEquals(model.getTestTemplateId(), tested.getTestTemplateId());
        assertEquals(model.getGradingTableId(), tested.getGradingTableId());
        assertEquals(model.getTestName(), tested.getTestName());
        assertEquals(model.getMaximumScore(), tested.getMaximumScore());
        assertEquals(model.getScore(), tested.getScore());
        assertEquals(model.getGrade(), tested.getGrade());
        Assertions.assertIterableEquals(model.getQuestions(), tested.getQuestions());
    }

}
