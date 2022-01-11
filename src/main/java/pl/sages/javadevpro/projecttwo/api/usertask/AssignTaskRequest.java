package pl.sages.javadevpro.projecttwo.api.usertask;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AssignTaskRequest {

    private final String userEmail;
    private final String taskId;

}
