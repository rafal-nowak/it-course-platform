package pl.sages.javadevpro.projecttwo.api.usertask;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserTaskRequest {

    private final String userId;
    private final String taskId;

}
