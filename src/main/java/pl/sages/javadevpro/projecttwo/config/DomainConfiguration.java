package pl.sages.javadevpro.projecttwo.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintRepository;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskExecutor;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.external.env.TaskEnvAdapter;
import pl.sages.javadevpro.projecttwo.external.env.kafka.KafkaUserTaskEnv;
import pl.sages.javadevpro.projecttwo.external.env.usertask.UserTaskEnvMapper;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository)  {
        return new UserService(userRepository);
    }

    @Bean
    public TaskBlueprintService taskService(TaskBlueprintRepository taskBlueprintRepository) {

        return new TaskBlueprintService(taskBlueprintRepository);
    }

    @Bean
    public TaskExecutor userTaskExecutor(KafkaUserTaskEnv userTaskExecutor, UserTaskEnvMapper userTaskExecMapper){
        return new TaskEnvAdapter(userTaskExecutor, userTaskExecMapper);}



}
