package pl.sages.javadevpro.projecttwo.api.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizControllerCommand {
    CommandName commandName;
    public Boolean hasName(CommandName commandName) {
       return this.commandName.equals(commandName);
    }
}
