package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.Value;

import java.util.List;

@Value
public class PageQuizDto {

    List<QuizDto> quizzes;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}