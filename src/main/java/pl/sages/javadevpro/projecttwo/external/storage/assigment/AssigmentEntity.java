package pl.sages.javadevpro.projecttwo.external.storage.assigment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AssigmentEntity {

    private String id;
    private String userId;
    private String taskId;

}

