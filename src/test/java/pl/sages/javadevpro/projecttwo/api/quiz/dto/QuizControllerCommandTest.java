package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizControllerCommandTest {

    @Test
    void has_name_should_return_true_when_command_has_given_name() {
        QuizControllerCommand quizControllerCommand = new QuizControllerCommand(CommandName.CHECK);
        assertEquals(true, quizControllerCommand.hasName(CommandName.CHECK));
    }

    @Test
    void has_name_should_return_false_when_command_has_not_given_name() {
        QuizControllerCommand quizControllerCommand = new QuizControllerCommand(CommandName.CHECK);
        assertEquals(false, quizControllerCommand.hasName(CommandName.RATE));
    }
}