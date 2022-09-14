package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizAssigmentRequest {

    private final String userId;
    private final String quizTemplateId;

}
