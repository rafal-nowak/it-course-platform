package pl.sages.javadevpro.projecttwo.config;


import org.mapstruct.factory.Mappers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentRepository;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintRepository;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskExecutor;
import pl.sages.javadevpro.projecttwo.domain.task.TaskRepository;
import pl.sages.javadevpro.projecttwo.domain.task.TaskService;
import pl.sages.javadevpro.projecttwo.domain.task.Workspace;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.external.env.TaskEnvAdapter;
import pl.sages.javadevpro.projecttwo.external.env.kafka.KafkaUserTaskEnv;
import pl.sages.javadevpro.projecttwo.external.env.usertask.UserTaskEnvMapper;
import pl.sages.javadevpro.projecttwo.external.storage.assigment.AssigmentEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.assigment.AssigmentMongoRepository;
import pl.sages.javadevpro.projecttwo.external.storage.assigment.AssigmentStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintMongoRepository;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskEntityStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskMongoRepository;
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
    public TaskBlueprintRepository taskBlueprintRepository(TaskBlueprintMongoRepository taskRepository, TaskBlueprintEntityMapper mapper ) {
        return new TaskBlueprintStorageAdapter( taskRepository, mapper );
    }

    @Bean
    public TaskBlueprintService taskBlueprintService(TaskBlueprintRepository taskBlueprintRepository) {

        return new TaskBlueprintService(taskBlueprintRepository);
    }


    @Bean
    public TaskExecutor userTaskExecutor(KafkaUserTaskEnv userTaskExecutor, UserTaskEnvMapper userTaskExecMapper){
        return new TaskEnvAdapter(userTaskExecutor, userTaskExecMapper);}


    @Bean
    public TaskRepository taskRepository(TaskMongoRepository taskMongoRepository, TaskEntityMapper mapper)  {
        return new TaskEntityStorageAdapter(taskMongoRepository, mapper);
    }

    @Bean
    public TaskService taskService(TaskRepository taskRepository, Workspace workspace, TaskBlueprintService taskBlueprintService)  {
        return new TaskService(taskRepository, workspace, taskBlueprintService);
    }

    @Bean
    public AssigmentRepository assigmentRepository(AssigmentMongoRepository assigmentMongoRepository, AssigmentEntityMapper mapper)  {
        return new AssigmentStorageAdapter(assigmentMongoRepository, mapper);
    }

    @Bean
    public AssigmentService assigmentService(AssigmentRepository assigmentRepository, TaskService taskService)  {
        return new AssigmentService(assigmentRepository, taskService);
    }

}
