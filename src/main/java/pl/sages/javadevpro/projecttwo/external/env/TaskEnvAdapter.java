package pl.sages.javadevpro.projecttwo.external.env;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.sages.javadevpro.projecttwo.domain.task.Task;
import pl.sages.javadevpro.projecttwo.domain.task.TaskExecutor;
import pl.sages.javadevpro.projecttwo.external.env.kafka.KafkaTaskEnv;
import pl.sages.javadevpro.projecttwo.external.env.task.TaskEnvMapper;

@RequiredArgsConstructor
@Log
public class TaskEnvAdapter implements TaskExecutor {

   private final KafkaTaskEnv taskExecutor;
   private final TaskEnvMapper taskEnvMapper;

    @Override
    public void exec(Task task) {
        taskExecutor.send(taskEnvMapper.toDto(task));
    }

}
