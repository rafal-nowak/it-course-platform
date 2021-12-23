package pl.sages.javadevpro.projecttwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
        path = "/{email}",
        produces = "application/json",
        consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "email") String email) {
        User user = userService.getUser(email);

        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }

    @PostMapping(
        produces = "application/json",
        consumes = "application/json"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        User user = userService.saveUser(dtoMapper.toDomain(dto));
        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }

    @GetMapping(
        path = "/me",
        produces = "application/json",
        consumes = "application/json"
    )
    @Secured("ROLE_STUDENT")
    public ResponseEntity<UserDto> aboutMe(Authentication authentication) {
        User user = userService.getUser((String) authentication.getPrincipal());
        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }
}
