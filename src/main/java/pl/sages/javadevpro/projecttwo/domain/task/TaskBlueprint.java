package pl.sages.javadevpro.projecttwo.domain.task;

import lombok.Value;

@Value
public class TaskBlueprint {

    String id;
    String name;
    String description;
    String repositoryUrl;

}