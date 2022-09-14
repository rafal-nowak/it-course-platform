package pl.sages.javadevpro.projecttwo.api.quiz.verification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// TODO zastanowić się nad nazwą - done
public @interface AuthVerifyQuizTemplate {

    String quizTemplateIdParamName() default "quizTemplateId";

}
