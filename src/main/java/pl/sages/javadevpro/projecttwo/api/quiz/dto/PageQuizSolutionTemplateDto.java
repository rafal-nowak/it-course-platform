package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.Value;

import java.util.List;

@Value
public class PageQuizSolutionTemplateDto {

    List<QuizSolutionTemplateDto> quizSolutionTemplates;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}