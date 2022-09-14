package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.QuizTemplateFactory;
import pl.sages.javadevpro.projecttwo.TestUserFactory;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizAssigmentRequest;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;

public class QuizAssignControllerIT extends BaseIT {

    @Autowired
    private QuizTemplateService quizTemplateService;
    @Autowired
    UserService userService;

    @Test
    void admin_should_be_able_to_assign_quiz_to_user() {
        //given
        String adminAccessToken = getTokenForAdmin();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        String userId = "UID123";

        quizTemplateService.save(quizTemplate);

        QuizAssigmentRequest quizAssigmentRequest = new QuizAssigmentRequest(
                userId,
                quizTemplate.getTestTemplateId()
        );

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-assign/",
                adminAccessToken,
                quizAssigmentRequest,
                MessageResponse.class);

        //then
        MessageResponse body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void student_should_not_be_able_to_assign_quiz_to_user() {
        //given
        User user = TestUserFactory.createStudent();
        QuizTemplate quizTemplate = QuizTemplateFactory.createRandom();
        String userId = "UID123";

        userService.save(user);
        quizTemplateService.save(quizTemplate);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        QuizAssigmentRequest quizAssigmentRequest = new QuizAssigmentRequest(
                userId,
                quizTemplate.getTestTemplateId()
        );

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/quiz-assign/",
                token,
                quizAssigmentRequest,
                MessageResponse.class);

        //then
        MessageResponse body = response.getBody();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
