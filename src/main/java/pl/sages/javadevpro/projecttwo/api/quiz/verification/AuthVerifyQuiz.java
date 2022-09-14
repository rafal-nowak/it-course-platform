package pl.sages.javadevpro.projecttwo.api.quiz.verification;

import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizStatusDto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// TODO zastanowić się nad nazwą - done
public @interface AuthVerifyQuiz {

    String quizIdParamName() default "quizId";
    String quizDtoParamName() default "quizDto";

}
