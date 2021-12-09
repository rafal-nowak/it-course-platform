package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.api.user.UserDto;
import pl.sages.javadevpro.projecttwo.api.user.UserDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.User;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserEndpoint {

    private final UserService userService;
    private final UserDtoMapper dtoMapper;

    @GetMapping(
        path = "/{login}",
        produces = "application/json",
        consumes = "application/json"
    )
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "login") String login) {
        User user = userService.getUser(login);

        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }

    @PostMapping(
        produces = "application/json",
        consumes = "application/json"
    )

    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        User user = userService.saveUser(dtoMapper.toDomain(dto));

        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }
}
