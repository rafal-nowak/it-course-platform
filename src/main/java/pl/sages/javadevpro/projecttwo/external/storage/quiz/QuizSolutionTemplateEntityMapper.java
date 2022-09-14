package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@Mapper(componentModel = "spring")
public interface QuizSolutionTemplateEntityMapper {

    QuizSolutionTemplateEntity toEntity(QuizSolutionTemplate domain);

    QuizSolutionTemplate toDomain(QuizSolutionTemplateEntity entity);

}
