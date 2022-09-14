package pl.sages.javadevpro.projecttwo.api.quiz.copy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizAssigmentRequest;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.quiz.assigment.QuizAssigmentService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/quiz-assign")
public class QuizAssignController {

    private final QuizAssigmentService assigmentService;

    @PostMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseEntity<MessageResponse> assignTaskToUser(@RequestBody QuizAssigmentRequest assigmentRequest) {
        assigmentService.assignNewQuiz(assigmentRequest.getUserId(), assigmentRequest.getQuizTemplateId());
        return ResponseEntity.ok(new MessageResponse("Quiz assigned to user"));
    }
}
