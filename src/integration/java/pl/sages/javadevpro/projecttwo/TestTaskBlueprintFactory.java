package pl.sages.javadevpro.projecttwo;

import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;

public class TestTaskBlueprintFactory {

    private static int sequence = 0;

    public static TaskBlueprint createRandom() {
        sequence++;
        return new TaskBlueprint(
                "TEST" + sequence,
                "Task Name " + sequence,
                "Task description " + sequence,
                "https://github.com/some-reporitory-" + sequence
        );
    }

    public static TaskBlueprint createWithValidUrl(String repositoryUrl) {
        sequence++;
        return new TaskBlueprint(
                "TEST" + sequence,
                "Task Name " + sequence,
                "Task description " + sequence,
                repositoryUrl
        );
    }

}
