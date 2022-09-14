package pl.sages.javadevpro.projecttwo.api.quiz.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageQuizDtoMapper {

    @Mapping(target = "quizzes", qualifiedByName = "toQuizDtoList")
    PageQuizDto toPageDto(PageQuiz domain);

    @Named("toQuizDtoList")
    @IterableMapping(qualifiedByName = "quizToQuizDto")
    List<QuizDto> toListDto(List<Quiz> quizzes);

    @Named("quizToQuizDto")
    QuizDto toDto(Quiz domain);
}