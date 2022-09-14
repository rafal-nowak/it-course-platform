package pl.sages.javadevpro.projecttwo.api.quiz.mapper;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizTemplateDto;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@Mapper(componentModel = "spring")
public interface QuizSolutionTemplateDtoMapper {
    QuizSolutionTemplateDto toDto(QuizSolutionTemplate domain);

    QuizSolutionTemplate toDomain(QuizSolutionTemplateDto dto);
}
