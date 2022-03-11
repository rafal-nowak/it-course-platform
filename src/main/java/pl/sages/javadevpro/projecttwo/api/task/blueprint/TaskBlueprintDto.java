package pl.sages.javadevpro.projecttwo.api.task.blueprint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskBlueprintDto {

    private String id;
    private String name;
    private String description;
    private String repositoryUrl;

}
