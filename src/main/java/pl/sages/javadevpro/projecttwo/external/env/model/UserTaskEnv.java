package pl.sages.javadevpro.projecttwo.external.env.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskEnv {

//    private String taskId;
//    private String userId;
//    private String taskPath;
//    private String taskStatus;

    private String id;
    private String name;
    private String description;
    private String userTaskFolder;
    private UserTaskStatusEnv taskStatus;
    private String userEmail;

}
