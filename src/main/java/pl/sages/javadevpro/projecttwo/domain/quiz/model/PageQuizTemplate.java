package pl.sages.javadevpro.projecttwo.domain.quiz.model;

import lombok.Value;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;

import java.io.Serializable;
import java.util.List;

@Value
public class PageQuizTemplate implements Serializable {

    List<QuizTemplate> quizTemplates;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}