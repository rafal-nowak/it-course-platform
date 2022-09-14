package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class QuizDto {
    String testId;
    String gradingTableId;
    String userId;
    String testTemplateId;
    Integer maximumScore;
    Integer score;
    String grade;
    String testName;
    QuizStatusDto status;
    List<QuestionDto> questions;
}
