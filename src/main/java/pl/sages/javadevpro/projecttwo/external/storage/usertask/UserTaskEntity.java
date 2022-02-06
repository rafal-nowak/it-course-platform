package pl.sages.javadevpro.projecttwo.external.storage.usertask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserTaskEntity {

    private String id;
    private String name;
    private String description;
    private String userTaskFolder;
    private String taskStatus;
}
