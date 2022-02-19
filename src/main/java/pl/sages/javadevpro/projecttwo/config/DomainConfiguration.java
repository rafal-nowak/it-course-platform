package pl.sages.javadevpro.projecttwo.config;


import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sages.javadevpro.projecttwo.api.task.TaskBlueprintDtoMapper;
import pl.sages.javadevpro.projecttwo.api.user.UserDtoMapper;
import pl.sages.javadevpro.projecttwo.api.usertask.UserTaskDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.UserTaskService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintRepository;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.domain.usertask.DirectoryService;
import pl.sages.javadevpro.projecttwo.domain.usertask.GitService;
import pl.sages.javadevpro.projecttwo.domain.usertask.UserTaskExecutor;
import pl.sages.javadevpro.projecttwo.external.directory.LocalDirectoryService;
import pl.sages.javadevpro.projecttwo.external.env.UserTaskEnvAdapter;
import pl.sages.javadevpro.projecttwo.external.env.kafka.KafkaUserTaskEnv;
import pl.sages.javadevpro.projecttwo.external.env.usertask.UserTaskEnvMapper;
import pl.sages.javadevpro.projecttwo.external.git.JGitService;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintMongoRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.MongoUserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.usertask.UserTaskEntityMapper;

import java.util.Map;

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
    public UserDtoMapper userDtoMapper() {
        return Mappers.getMapper(UserDtoMapper.class);
    }

    @Bean
    public TaskBlueprintDtoMapper taskDtoMapper() {
        return Mappers.getMapper(TaskBlueprintDtoMapper.class);
    }

    @Bean
    public TaskBlueprintEntityMapper taskEntityMapper() {
        return Mappers.getMapper(TaskBlueprintEntityMapper.class);
    }

    @Bean
    public UserTaskDtoMapper userTaskDtoMapper() {
        return Mappers.getMapper(UserTaskDtoMapper.class);
    }

    @Bean
    public UserTaskEntityMapper userTaskEntityMapper() {
        return Mappers.getMapper(UserTaskEntityMapper.class);
    }

    @Bean
    public UserTaskService userTaskService(
            GitService gitService,
            DirectoryService directoryService,
            UserService userService,
            TaskBlueprintService taskBlueprintService,
            UserTaskExecutor userTaskExecutor
    ) {
        return new UserTaskService(
                gitService,
                directoryService,
                userService,
            taskBlueprintService,
                userTaskExecutor);
    }

    @Bean
    public UserTaskExecutor userTaskExecutor(KafkaUserTaskEnv userTaskExecutor, UserTaskEnvMapper userTaskExecMapper){
        return new UserTaskEnvAdapter(userTaskExecutor, userTaskExecMapper);}

    @Bean
    UserTaskEnvMapper userTaskEnvMapper() { return Mappers.getMapper(UserTaskEnvMapper.class); }

    @Bean
    public GitService gitService() {
        return new JGitService();
    }

    @Bean
    public DirectoryService directoryService(@Value("${local.folder}") String baseLocalFolder){
        return new LocalDirectoryService(baseLocalFolder);
    }


}
