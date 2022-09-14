package pl.sages.javadevpro.projecttwo.domain.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuestionDto;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class Quiz {
    String testId;
    String gradingTableId;
    String userId;
    String testTemplateId;
    Integer maximumScore;
    Integer score;
    String grade;
    String testName;
    QuizStatus status;
    List<Question> questions;
}
