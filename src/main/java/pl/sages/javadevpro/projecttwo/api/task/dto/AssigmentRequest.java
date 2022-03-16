package pl.sages.javadevpro.projecttwo.api.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssigmentRequest {

    private final String userId;
    private final String taskId;

}
