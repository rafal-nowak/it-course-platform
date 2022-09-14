package pl.sages.javadevpro.projecttwo.api.quiz.mapper;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizTemplateDto;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@Mapper(componentModel = "spring")
public interface QuizDtoMapper {
    QuizDto toDto(Quiz domain);

    Quiz toDomain(QuizDto dto);
}
