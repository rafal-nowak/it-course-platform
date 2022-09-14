package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class QuizTemplateDto {
    String testTemplateId;
    String gradingTableId;
    Integer maximumScore;
    Integer score;
    String grade;
    String testName;
    List<QuestionDto> questions;
}
