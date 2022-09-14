package pl.sages.javadevpro.projecttwo.domain.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuestionDto;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class QuizTemplate {
    String testTemplateId;
    String gradingTableId;
    Integer maximumScore;
    Integer score;
    String grade;
    String testName;
    List<Question> questions;
}
