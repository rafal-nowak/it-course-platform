package pl.sages.javadevpro.projecttwo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}
