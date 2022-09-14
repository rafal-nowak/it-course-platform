package pl.sages.javadevpro.projecttwo.api;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.QuizFactory;
import pl.sages.javadevpro.projecttwo.QuizSolutionTemplateFactory;
import pl.sages.javadevpro.projecttwo.QuizTemplateFactory;
import pl.sages.javadevpro.projecttwo.TestUserFactory;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.CommandName;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizControllerCommand;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.PageQuizDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.QuizDtoMapper;
import pl.sages.javadevpro.projecttwo.api.usertask.ErrorResponse;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizService;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizSolutionTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuizControllerIT extends BaseIT {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizDtoMapper quizDtoMapper;
    @Autowired
    private PageQuizDtoMapper pageQuizDtoMapper;
    @Autowired
    UserService userService;

    @Autowired
    QuizTemplateService quizTemplateService;
    @Autowired
    QuizSolutionTemplateService quizSolutionTemplateService;

    @Test
    void admin_should_be_able_to_get_information_about_quiz() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Quiz quiz = QuizFactory.createRandom();
        quizService.save(quiz);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quizzes/" + quiz.getTestId(),
                adminAccessToken,
                null,
                QuizDto.class);

        //then
        QuizDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareQuizzes(quiz, quizDtoMapper.toDomain(body));
    }

    @Test
    void user_should_be_able_to_get_information_about_quiz_assigned_to_this_user() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz = QuizFactory.createRandom();
        quiz.setUserId(user.getId());
        userService.save(user);
        quizService.save(quiz);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quizzes/" + quiz.getTestId(),
                token,
                null,
                QuizDto.class);

        //then
        QuizDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareQuizzes(quiz, quizDtoMapper.toDomain(body));
    }

    @Test
    void user_should_not_be_able_to_get_information_about_quiz_not_assigned_to_this_user() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz = QuizFactory.createRandom();
        quiz.setUserId(user.getId() + "qwerty");
        userService.save(user);
        quizService.save(quiz);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quizzes/" + quiz.getTestId(),
                token,
                null,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_get_information_about_all_quizzes() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Quiz quiz1 = QuizFactory.createRandom();
        Quiz quiz2 = QuizFactory.createRandom();
        Quiz quiz3 = QuizFactory.createRandom();
        quizService.save(quiz1);
        quizService.save(quiz2);
        quizService.save(quiz3);


        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quizzes",
                adminAccessToken,
                null,
                PageQuizDto.class);

        //then
        PageQuizDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        compareQuizzes(quiz1, quizDtoMapper.toDomain(body.getQuizzes().get(0)));
        compareQuizzes(quiz2, quizDtoMapper.toDomain(body.getQuizzes().get(1)));
        compareQuizzes(quiz3, quizDtoMapper.toDomain(body.getQuizzes().get(2)));
    }

    @Test
    void student_should_be_able_to_get_information_about_all_quizzes_assigned_to_this_user() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz1 = QuizFactory.createRandom();
        Quiz quiz2 = QuizFactory.createRandom();
        Quiz quiz3 = QuizFactory.createRandom();
        quiz1.setUserId(user.getId());
        quiz2.setUserId(user.getId());
        quiz3.setUserId(user.getId() + "qwerty");
        userService.save(user);
        quizService.save(quiz1);
        quizService.save(quiz2);
        quizService.save(quiz3);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());


        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quizzes",
                token,
                null,
                PageQuizDto.class);

        //then
        PageQuizDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(2, body.getTotalElements());
        compareQuizzes(quiz1, quizDtoMapper.toDomain(body.getQuizzes().get(0)));
        compareQuizzes(quiz2, quizDtoMapper.toDomain(body.getQuizzes().get(1)));
    }

    @Test
    void admin_should_be_able_to_save_new_quiz() {
        //given
        Quiz quiz = QuizFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quizzes",
                adminAccessToken,
                quizDtoMapper.toDto(quiz),
                QuizDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        QuizDto body = response.getBody();
        //and
        compareQuizzes(quiz, quizDtoMapper.toDomain(body));
    }

    @Test
    void student_should_not_be_able_to_save_new_quiz() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz = QuizFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quizzes",
                token,
                quizDtoMapper.toDto(quiz),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void should_return_conflict_about_duplicated_quiz(){
        //given
        Quiz quiz = QuizFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        quizService.save(quiz);
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quizzes",
                adminAccessToken,
                quizDtoMapper.toDto(quiz),
                ErrorResponse.class);
        //then
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_quiz() {
        //given
        Quiz quiz = QuizFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        quizService.save(quiz);
        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/quizzes/" + quiz.getTestTemplateId(),
                adminAccessToken,
                null,
                Void.class);
        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void student_should_not_be_able_to_delete_quiz() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz = QuizFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        quizService.save(quiz);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/quizzes/" + quiz.getTestTemplateId(),
                token,
                null,
                ErrorResponse.class
        );

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_update_quiz() {
        //given
        Quiz quiz = QuizFactory.createRandom();
        Quiz updatedQuiz = new Quiz(
                quiz.getTestId(),
                "Grading table id is updated",
                "User id is updated",
                "Test template id is updated",
                600,
                400,
                "Grade is updated",
                "Table Name is updated",
                QuizStatus.ASSIGNED,
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
        quizService.save(quiz);

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/quizzes",
                adminAccessToken,
                quizDtoMapper.toDto(updatedQuiz),
                QuizDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        QuizDto body = response.getBody();
        Assertions.assertNull(body);
    }

    @Test
    void user_should_be_able_to_update_quiz_assigned_to_this_user_when_it_uploads_the_solution_for_the_first_time() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz = QuizFactory.createRandom();
        quiz.setStatus(QuizStatus.ASSIGNED);
        quiz.setUserId(user.getId());
        userService.save(user);
        quizService.save(quiz);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        Quiz updatedQuiz = new Quiz(
                quiz.getTestId(),
                "Grading table id is updated",
                user.getId(),
                "Test template id is updated",
                600,
                400,
                "Grade is updated",
                "Table Name is updated",
                QuizStatus.SUBMITTED,
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

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/quizzes",
                token,
                quizDtoMapper.toDto(updatedQuiz),
                QuizDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        QuizDto body = response.getBody();
        Assertions.assertNull(body);
    }

    @Test
    void user_should_not_be_able_to_update_quiz_assigned_to_this_user_when_it_does_not_submit_the_solution_for_the_first_time() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz = QuizFactory.createRandom();
        quiz.setUserId(user.getId());
        quiz.setStatus(QuizStatus.SUBMITTED);
        userService.save(user);
        quizService.save(quiz);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        Quiz updatedQuiz = new Quiz(
                quiz.getTestId(),
                "Grading table id is updated",
                user.getId(),
                "Test template id is updated",
                600,
                400,
                "Grade is updated",
                "Table Name is updated",
                QuizStatus.SUBMITTED,
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

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/quizzes",
                token,
                quizDtoMapper.toDto(updatedQuiz),
                QuizDto.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void user_should_not_be_able_to_update_quiz_not_assigned_to_this_user() {
        //given
        User user = TestUserFactory.createStudent();
        Quiz quiz = QuizFactory.createRandom();
        quiz.setUserId(user.getId() + "qwerty");
        userService.save(user);
        quizService.save(quiz);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        Quiz updatedQuiz = new Quiz(
                quiz.getTestId(),
                "Grading table id is updated",
                user.getId(),
                "Test template id is updated",
                600,
                400,
                "Grade is updated",
                "Table Name is updated",
                QuizStatus.ASSIGNED,
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

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/quizzes",
                token,
                quizDtoMapper.toDto(updatedQuiz),
                QuizDto.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void should_get_response_code_404_when_quiz_not_exits() {
        //given
        String adminAccessToken = getTokenForAdmin();
        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/quizzes/1",
                adminAccessToken,
                null,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



    @Test
    void admin_should_be_able_to_execute_quiz_command() {
        //given
        QuizControllerCommand body = new QuizControllerCommand(CommandName.CHECK);
        String adminAccessToken = getTokenForAdmin();
        Quiz quiz = QuizFactory.createRandom();
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        quiz.setTestTemplateId(quizTemplate.getTestTemplateId());
        quizSolutionTemplate.setTestTemplateId(quizTemplate.getTestTemplateId());
        quizService.save(quiz);
        quizTemplateService.save(quizTemplate);
        quizSolutionTemplateService.save(quizSolutionTemplate);

        //when
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/quizzes/" + quiz.getTestId() + "/commands",
                adminAccessToken,
                body,
                MessageResponse.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedMessage = "Quiz " + quiz.getTestId()
                + " executed command: " + body.getCommandName().name()
                +  " status: " + QuizStatus.CHECKED;
        assertEquals(expectedMessage, response.getBody().getMessage());
    }

    @Test
    void user_should_be_able_to_execute_quiz_command_for_quiz_assigned_to_this_user() {
        //given
        User user = TestUserFactory.createStudent();
        QuizControllerCommand body = new QuizControllerCommand(CommandName.CHECK);
        Quiz quiz = QuizFactory.createRandom();
        quiz.setUserId(user.getId());
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        quiz.setTestTemplateId(quizTemplate.getTestTemplateId());
        quizSolutionTemplate.setTestTemplateId(quizTemplate.getTestTemplateId());
        quizService.save(quiz);
        quizTemplateService.save(quizTemplate);
        quizSolutionTemplateService.save(quizSolutionTemplate);
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/quizzes/" + quiz.getTestId() + "/commands",
                token,
                body,
                MessageResponse.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedMessage = "Quiz " + quiz.getTestId()
                + " executed command: " + body.getCommandName().name()
                +  " status: " + QuizStatus.CHECKED;
        assertEquals(expectedMessage, response.getBody().getMessage());
    }

    @Test
    void user_should_not_be_able_to_execute_quiz_command_for_quiz_not_assigned_to_this_user() {
        //given
        User user = TestUserFactory.createStudent();
        QuizControllerCommand body = new QuizControllerCommand(CommandName.CHECK);
        Quiz quiz = QuizFactory.createRandom();
        quiz.setUserId(user.getId() + "qwerty");
        QuizSolutionTemplate quizSolutionTemplate = QuizSolutionTemplateFactory.createRandom();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        quiz.setTestTemplateId(quizTemplate.getTestTemplateId());
        quizSolutionTemplate.setTestTemplateId(quizTemplate.getTestTemplateId());
        quizService.save(quiz);
        quizTemplateService.save(quizTemplate);
        quizSolutionTemplateService.save(quizSolutionTemplate);
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/quizzes/" + quiz.getTestId() + "/commands",
                token,
                body,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    private void compareQuizzes(Quiz model, Quiz tested) {
        assertNotNull(tested);
        assertEquals(model.getTestId(), tested.getTestId());
        assertEquals(model.getTestTemplateId(), tested.getTestTemplateId());
        assertEquals(model.getGradingTableId(), tested.getGradingTableId());
        assertEquals(model.getTestName(), tested.getTestName());
        assertEquals(model.getMaximumScore(), tested.getMaximumScore());
        assertEquals(model.getScore(), tested.getScore());
        assertEquals(model.getGrade(), tested.getGrade());
        Assertions.assertIterableEquals(model.getQuestions(), tested.getQuestions());
    }

}
