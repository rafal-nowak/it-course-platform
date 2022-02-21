package pl.sages.javadevpro.projecttwo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;

public class TaskBlueprintServiceIT extends BaseIT {

    @Autowired
    TaskBlueprintService service;

    @Test
    public void add_task_test() {
        //given
        TaskBlueprint taskBlueprint = new TaskBlueprint(
                "1",
                "Task Name 1",
                "Task Description 1",
                "https://github.com/some-reporitory-1"
        );
        service.save(taskBlueprint);

        //when
        TaskBlueprint readTaskBlueprint = service.findBy(taskBlueprint.getId());

        //then
        Assertions.assertEquals(taskBlueprint.getId(), readTaskBlueprint.getId());
        Assertions.assertEquals(taskBlueprint.getName(), readTaskBlueprint.getName());
        Assertions.assertEquals(taskBlueprint.getDescription(), readTaskBlueprint.getDescription());
        Assertions.assertEquals(taskBlueprint.getRepositoryUrl(), readTaskBlueprint.getRepositoryUrl());
//        Assertions.assertNotEquals(taskBlueprint, readTaskBlueprint);
    }

    @Test
    public void get_id_should_return_correct_user() {
        //given
        TaskBlueprint taskBlueprint1 = new TaskBlueprint(
                "2",
                "Task Name 2",
                "Task Description 2",
                "https://github.com/some-reporitory-2"
        );
        TaskBlueprint taskBlueprint2 = new TaskBlueprint(
                "3",
                "Task Name 3",
                "Task Description 3",
                "https://github.com/some-reporitory-3"
        );
        TaskBlueprint taskBlueprint3 = new TaskBlueprint(
                "4",
                "Task Name 4",
                "Task Description 4",
                "https://github.com/some-reporitory-4"
        );
        service.save(taskBlueprint1);
        service.save(taskBlueprint2);
        service.save(taskBlueprint3);

        //when
        TaskBlueprint readTaskBlueprint = service.findBy(taskBlueprint2.getId());

        //then
        Assertions.assertEquals(taskBlueprint2.getId(), readTaskBlueprint.getId());
        Assertions.assertEquals(taskBlueprint2.getName(), readTaskBlueprint.getName());
        Assertions.assertEquals(taskBlueprint2.getDescription(), readTaskBlueprint.getDescription());
        Assertions.assertEquals(taskBlueprint2.getRepositoryUrl(), readTaskBlueprint.getRepositoryUrl());

    }

}
