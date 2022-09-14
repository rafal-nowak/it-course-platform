package pl.sages.javadevpro.projecttwo.api.quiz.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizTemplateDto;
import pl.sages.javadevpro.projecttwo.api.user.dto.PageUserDto;
import pl.sages.javadevpro.projecttwo.api.user.dto.UserDto;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.user.model.PageUser;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageQuizTemplateDtoMapper {

    @Mapping(target = "quizTemplates", qualifiedByName = "toQuizTemplateDtoList")
    PageQuizTemplateDto toPageDto(PageQuizTemplate domain);

    @Named("toQuizTemplateDtoList")
    @IterableMapping(qualifiedByName = "quizTemplateToQuizTemplateDto")
    List<QuizTemplateDto> toListDto(List<QuizTemplate> quizTemplates);

    @Named("quizTemplateToQuizTemplateDto")
    QuizTemplateDto toDto(QuizTemplate domain);
}