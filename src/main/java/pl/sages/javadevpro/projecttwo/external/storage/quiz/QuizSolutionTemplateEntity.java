package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;

import java.util.List;
import java.util.Objects;

@Document("QuizSolutionTemplates")
@TypeAlias("QuizSolutionTemplateEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizSolutionTemplateEntity {
    @Id
    private String testSolutionId;
    @Indexed(unique = true)
    private String testTemplateId;
    private Integer maximumScore;
    private Integer score;
    private String grade;
    private String testName;
    private List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizSolutionTemplateEntity that = (QuizSolutionTemplateEntity) o;

        return Objects.equals(testSolutionId, that.testSolutionId);
    }

    @Override
    public int hashCode() {
        return testSolutionId != null ? testSolutionId.hashCode() : 0;
    }
}
