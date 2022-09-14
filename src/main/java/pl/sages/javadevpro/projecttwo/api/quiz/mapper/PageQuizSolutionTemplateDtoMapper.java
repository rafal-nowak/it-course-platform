package pl.sages.javadevpro.projecttwo.api.quiz.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizTemplateDto;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageQuizSolutionTemplateDtoMapper {

    @Mapping(target = "quizSolutionTemplates", qualifiedByName = "toQuizSolutionTemplateDtoList")
    PageQuizSolutionTemplateDto toPageDto(PageQuizSolutionTemplate domain);

    @Named("toQuizSolutionTemplateDtoList")
    @IterableMapping(qualifiedByName = "quizSolutionTemplateToQuizSolutionTemplateDto")
    List<QuizSolutionTemplateDto> toListDto(List<QuizSolutionTemplate> quizSolutionTemplates);

    @Named("quizSolutionTemplateToQuizSolutionTemplateDto")
    QuizSolutionTemplateDto toDto(QuizSolutionTemplate domain);
}