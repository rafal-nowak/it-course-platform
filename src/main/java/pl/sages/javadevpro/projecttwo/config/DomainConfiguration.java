package pl.sages.javadevpro.projecttwo.config;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sages.javadevpro.projecttwo.api.task.TaskDtoMapper;
import pl.sages.javadevpro.projecttwo.api.user.UserDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.TaskService;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskRepository;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.task.JpaTaskRepository;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.TaskStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.UserStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.user.JpaUserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntityMapper;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {


    @Bean
    public UserRepository userRepository(
            JpaUserRepository jpaUserRepository,
            UserEntityMapper mapper
    ) {
        return new UserStorageAdapter(
                jpaUserRepository,
                mapper
        );
    }


    @Bean
    public UserService userService(
        UserRepository userRepository
    )  {
        return new UserService(
            userRepository
        );
    }

    @Bean
    public TaskRepository taskRepository(
            JpaTaskRepository jpaTaskRepository,
            TaskEntityMapper mapper
    ) {
        return new TaskStorageAdapter(
                jpaTaskRepository,
                mapper
        );
    }


    @Bean
    public TaskService taskService(
            TaskRepository taskRepository
    )  {
        return new TaskService(
                taskRepository
        );
    }

    @Bean
    public UserDtoMapper userDtoMapper() {
        return Mappers.getMapper(UserDtoMapper.class);
    }

    @Bean
    public UserEntityMapper userEntityMapper() {
        return Mappers.getMapper(UserEntityMapper.class);
    }

    @Bean
    public TaskDtoMapper taskDtoMapper() {
        return Mappers.getMapper(TaskDtoMapper.class);
    }

    @Bean
    public TaskEntityMapper taskEntityMapper() {
        return Mappers.getMapper(TaskEntityMapper.class);
    }
}
