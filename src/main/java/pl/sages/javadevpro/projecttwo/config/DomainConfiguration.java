package pl.sages.javadevpro.projecttwo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.external.StorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.InMemoryRepository;
import pl.sages.javadevpro.projecttwo.external.storage.UserEntityMapper;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {


    @Bean
    public UserRepository userRepository(
        InMemoryRepository inMemoryRepository,
        UserEntityMapper mapper
    ) {
        return new StorageAdapter(
            inMemoryRepository,
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
}
