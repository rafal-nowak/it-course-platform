package pl.sages.javadevpro.projecttwo.external.storage.user;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.sages.javadevpro.projecttwo.api.user.UserDto;
import pl.sages.javadevpro.projecttwo.api.user.UserDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final UserEntityMapper userEntityMapper = Mappers.getMapper(UserEntityMapper.class);
    private final UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);

    private final static String USER_NAME = "User1";
    private final static String USER_EMAIL = "user1@email.com";
    private final static String USER_PASSWORD = "password";
    private final static List<String> USER_ROLES = List.of("STUDENT", "ADMIN");


    @Test
    void user_entity_values_should_be_mapped_directly_to_domain(){
        UserEntity entity = UserEntity.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .roles(USER_ROLES)
                .build();
        User domain = userEntityMapper.toDomain(entity);

        assertEquals(USER_NAME, domain.getName());
        assertEquals(USER_EMAIL, domain.getEmail());
        assertEquals(USER_PASSWORD, domain.getPassword());
        assertEquals(USER_ROLES, domain.getRoles());
    }

    @Test
    void password_should_be_hidden_while_mapping_from_domain_to_dto() {
        User domain = new User();
        domain.setName(USER_NAME);
        domain.setPassword(USER_PASSWORD);
        UserDto dto = userDtoMapper.toDto(domain);

        assertEquals("######", dto.getPassword());
        assertEquals(USER_NAME, dto.getName());
    }

    @Test
    void user_dto_values_should_be_mapped_directly_to_domain(){
        UserDto dto = new UserDto();
        dto.setName(USER_NAME);
        dto.setPassword(USER_PASSWORD);
        dto.setEmail(USER_EMAIL);
        dto.setRoles(USER_ROLES);
        User domain = userDtoMapper.toDomain(dto);

        assertEquals(USER_NAME, domain.getName());
        assertEquals(USER_EMAIL, domain.getEmail());
        assertEquals(USER_PASSWORD, domain.getPassword());
        assertEquals(USER_ROLES, domain.getRoles());
    }

    @Test
    void user_domain_values_should_be_mapped_directly_to_entity(){
        User domain = new User();
        domain.setName(USER_NAME);
        domain.setPassword(USER_PASSWORD);
        domain.setEmail(USER_EMAIL);
        domain.setRoles(USER_ROLES);

        UserEntity entity = userEntityMapper.toEntity(domain);

        assertEquals(USER_NAME, entity.getName());
        assertEquals(USER_EMAIL, entity.getEmail());
        assertEquals(USER_PASSWORD, entity.getPassword());
        assertEquals(USER_ROLES, entity.getRoles());
    }
}
