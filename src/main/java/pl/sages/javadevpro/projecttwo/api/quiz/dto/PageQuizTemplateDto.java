package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.Value;
import pl.sages.javadevpro.projecttwo.api.user.dto.UserDto;

import java.util.List;

@Value
public class PageQuizTemplateDto {

    List<QuizTemplateDto> quizTemplates;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}