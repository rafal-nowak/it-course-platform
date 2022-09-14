package pl.sages.javadevpro.projecttwo.domain.quiz.model;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageQuiz implements Serializable {

    List<Quiz> quizzes;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}