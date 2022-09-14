package pl.sages.javadevpro.projecttwo;

import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.List;

public class QuizTemplateFactory {

    private static int sequence = 0;

    public static QuizTemplate createRandom() {
        sequence++;
        return new QuizTemplate(
                "Test " + sequence,
                "Grading Table " + sequence,
                500,
                300,
                "Excellent " + sequence,
                "Test name " + sequence,
                List.of(
                        new Question(
                                1L,
                                4,
                                0,
                                QuestionType.SINGLE_CHOICE_CLOSED_QUESTION,
                                "Single choice question?",
                                List.of(
                                        "Single choice answer " + sequence,
                                        "Single choice answer " + sequence + 1,
                                        "Single choice answer " + sequence + 2,
                                        "Single choice answer " + sequence + 3
                                )
                        ),
                        new Question(
                                2L,
                                4,
                                0,
                                QuestionType.MULTI_CHOICE_CLOSED_QUESTION,
                                "Single choice question?",
                                List.of(
                                        "Multi choice answer " + sequence,
                                        "Multi choice answer " + sequence + 1,
                                        "Multi choice answer " + sequence + 2,
                                        "Multi choice answer " + sequence + 3
                                )
                        )
                )
        );
    }
}
