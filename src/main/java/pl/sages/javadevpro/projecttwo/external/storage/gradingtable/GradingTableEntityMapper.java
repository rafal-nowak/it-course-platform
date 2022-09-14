package pl.sages.javadevpro.projecttwo.external.storage.gradingtable;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizEntity;

@Mapper(componentModel = "spring")
public interface GradingTableEntityMapper {

    GradingTableEntity toEntity(GradingTable domain);

    GradingTable toDomain(GradingTableEntity entity);

}
