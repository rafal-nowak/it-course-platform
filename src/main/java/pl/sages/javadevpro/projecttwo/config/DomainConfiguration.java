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
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintMongoRepository;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.user.MongoUserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserStorageAdapter;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public UserRepository userRepository(MongoUserRepository mongoUserRepository, UserEntityMapper mapper) {
        return new UserStorageAdapter(mongoUserRepository, mapper);
    }

    @Bean
    public UserService userService(UserRepository userRepository)  {
        return new UserService(userRepository);
    }

    @Bean
    public TaskBlueprintRepository taskRepository(TaskBlueprintMongoRepository taskRepository, TaskBlueprintEntityMapper mapper ) {
        return new TaskBlueprintStorageAdapter( taskRepository, mapper );
    }

    @Bean
    public TaskBlueprintService taskService(TaskBlueprintRepository taskBlueprintRepository) {

        return new TaskBlueprintService(taskBlueprintRepository);
    }

    @Bean
    public TaskExecutor userTaskExecutor(KafkaUserTaskEnv userTaskExecutor, UserTaskEnvMapper userTaskExecMapper){
        return new TaskEnvAdapter(userTaskExecutor, userTaskExecMapper);}



}
