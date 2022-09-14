package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;

import java.util.List;
import java.util.Objects;

@Document("Quizzes")
@TypeAlias("QuizEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizEntity {
    @Id
    private String testId;
    private String gradingTableId;
    private String userId;
    private String testTemplateId;
    private Integer maximumScore;
    private Integer score;
    private String grade;
    private String testName;
    private QuizStatus status;
    private List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizEntity that = (QuizEntity) o;

        return Objects.equals(testId, that.testId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
