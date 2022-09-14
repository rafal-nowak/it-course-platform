package pl.sages.javadevpro.projecttwo.external.storage.gradingtable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuestionDto;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingRecord;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;

import java.util.List;
import java.util.Objects;

@Document("GradingTables")
@TypeAlias("GradingTableEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GradingTableEntity {
    @Id
    private String tableId;
    private String tableName;
    private List<GradingRecord> records;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradingTableEntity that = (GradingTableEntity) o;

        return Objects.equals(tableId, that.tableId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
