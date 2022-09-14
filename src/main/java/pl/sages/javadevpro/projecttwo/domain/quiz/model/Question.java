package pl.sages.javadevpro.projecttwo.domain.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuestionTypeDto;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class Question {
    Long questionId;
    Integer maximumScore;
    Integer score;
    QuestionType type;
    String question;
    List<String> answers;
}
