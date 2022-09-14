package pl.sages.javadevpro.projecttwo.domain.quiz.model;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageQuizSolutionTemplate implements Serializable {

    List<QuizSolutionTemplate> quizSolutionTemplates;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}