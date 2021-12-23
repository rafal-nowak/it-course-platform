package pl.sages.javadevpro.projecttwo.api.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {

    private Integer id;
    private String name;
    private String description;


}
