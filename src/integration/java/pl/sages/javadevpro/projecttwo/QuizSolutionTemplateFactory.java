package pl.sages.javadevpro.projecttwo;

import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;

import java.util.List;

public class QuizSolutionTemplateFactory {

    private static int sequence = 0;

    public static QuizSolutionTemplate createRandom() {
        sequence++;
        return new QuizSolutionTemplate(
                "Test " + sequence,
                "Test template " + sequence,
                500,
                100,
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
