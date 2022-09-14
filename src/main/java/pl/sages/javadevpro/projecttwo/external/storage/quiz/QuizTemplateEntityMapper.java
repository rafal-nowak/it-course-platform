package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@Mapper(componentModel = "spring")
public interface QuizTemplateEntityMapper {

    QuizTemplateEntity toEntity(QuizTemplate domain);

    QuizTemplate toDomain(QuizTemplateEntity entity);

}
