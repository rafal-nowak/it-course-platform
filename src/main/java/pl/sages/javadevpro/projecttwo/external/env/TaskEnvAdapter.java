package pl.sages.javadevpro.projecttwo.external.env;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.task.TaskExecutor;
import pl.sages.javadevpro.projecttwo.domain.task.TaskRepository;
import pl.sages.javadevpro.projecttwo.external.env.kafka.KafkaUserTaskEnv;
import pl.sages.javadevpro.projecttwo.external.env.usertask.UserTaskEnvMapper;

@RequiredArgsConstructor
@Log
public class TaskEnvAdapter implements TaskExecutor {

   private final KafkaUserTaskEnv userTaskExecutor;
   private final UserTaskEnvMapper execMapper;

    @Override
    public void exec(Task task) {
        userTaskExecutor.send(execMapper.toDto(task));
    }

}
