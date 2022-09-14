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

import java.util.List;
import java.util.Objects;

@Document("QuizTemplates")
@TypeAlias("QuizTemplateEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizTemplateEntity {
    @Id
    private String testTemplateId;
    private String gradingTableId;
    private Integer maximumScore;
    private Integer score;
    private String grade;
    private String testName;
    private List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizTemplateEntity that = (QuizTemplateEntity) o;
        return Objects.equals(testTemplateId, that.testTemplateId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
