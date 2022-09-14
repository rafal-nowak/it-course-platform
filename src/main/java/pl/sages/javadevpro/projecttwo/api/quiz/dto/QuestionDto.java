package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class QuestionDto {
    Long questionId;
    Integer maximumScore;
    Integer score;
    QuestionTypeDto type;
    String question;
    List<String> answers;
}
