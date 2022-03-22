package pl.sages.javadevpro.projecttwo.security.taskauthorization;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentService;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.security.UserPrincipal;

@Service
@RequiredArgsConstructor
class TaskAssignmentVerifier {

    private final UserService userService;
    private final AssigmentService assigmentService;

    public boolean userIsOwnerOfTask(String taskId, Authentication authentication) {
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());
        boolean authorizationConfirmed = assigmentService.isTaskAssignedToUser(user.getId(), taskId);
        if (authorizationConfirmed) {
            return true;
        } else {
            throw new UserIsNotAuthorizedToThisTaskException("User is not authorized to this task.");
        }
    }

}
