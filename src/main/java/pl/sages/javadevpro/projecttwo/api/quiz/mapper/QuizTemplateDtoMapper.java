package pl.sages.javadevpro.projecttwo.api.quiz.mapper;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizTemplateDto;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@Mapper(componentModel = "spring")
public interface QuizTemplateDtoMapper {
    QuizTemplateDto toDto(QuizTemplate domain);

    QuizTemplate toDomain(QuizTemplateDto dto);
}
