package pl.sages.javadevpro.projecttwo.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users",
        produces = "application/json",
        consumes = "application/json"
)
public class UserController {

    private final UserService userService;
    private final UserDtoMapper dtoMapper;

    @GetMapping( path = "/{email}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "email") String email) {
        User user = userService.findByEmail(email);

        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userList = userService.findAll().stream()  // TODO dadac do mappera
            .map(dtoMapper::toDto)
            .collect(Collectors.toList());

        return ResponseEntity
            .ok(userList);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        User user = userService.save(dtoMapper.toDomain(dto));
        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto) {
        User user = userService.update(dtoMapper.toDomain(dto));
        return ResponseEntity
                .ok(dtoMapper.toDto(user));
    }

    @DeleteMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> removeUser(@RequestBody UserDto dto){
        userService.remove(dtoMapper.toDomain(dto));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("me")
    @Secured("ROLE_STUDENT")
    public ResponseEntity<UserDto> aboutMe(Authentication authentication) {
        User user = userService.findByEmail((String) authentication.getPrincipal());
        return ResponseEntity
            .ok(dtoMapper.toDto(user));
    }
}