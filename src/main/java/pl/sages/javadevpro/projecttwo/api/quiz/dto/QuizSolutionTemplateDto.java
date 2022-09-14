package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class QuizSolutionTemplateDto {
    String testSolutionId;
    String testTemplateId;
    Integer maximumScore;
    Integer score;
    String grade;
    String testName;
    List<QuestionDto> questions;
}
