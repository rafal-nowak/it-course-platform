package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@Mapper(componentModel = "spring")
public interface QuizEntityMapper {

    QuizEntity toEntity(Quiz domain);

    Quiz toDomain(QuizEntity entity);

}
