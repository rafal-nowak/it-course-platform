package pl.sages.javadevpro.projecttwo.external.storage.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class TaskEntity {

    @Id
    private Integer id;
    private String name;
    private String description;

}
