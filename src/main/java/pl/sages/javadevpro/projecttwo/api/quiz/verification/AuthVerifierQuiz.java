package pl.sages.javadevpro.projecttwo.api.quiz.verification;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizService;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;
import pl.sages.javadevpro.projecttwo.security.UserPrincipal;

import java.util.Arrays;

import static pl.sages.javadevpro.projecttwo.domain.user.model.UserRole.ADMIN;

@Component
@Aspect
@RequiredArgsConstructor
class AuthVerifierQuiz {

    private final UserService userService;
    private final QuizService quizService;


    @Before(value = "@annotation(authVerifyQuiz)")
    public void userIsAdminOrOwnerOfTask(JoinPoint joinPoint, AuthVerifyQuiz authVerifyQuiz) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String quizId = (String) getParameterByName(joinPoint, authVerifyQuiz.quizIdParamName());
        QuizDto quizDto = (QuizDto) getParameterByName(joinPoint, authVerifyQuiz.quizDtoParamName());

        if(quizId == null && quizDto != null)
            quizId = quizDto.getTestId();

        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());
        boolean authorizationConfirmed = quizService.isQuizAssignedToUser(user.getId(), quizId) || user.getRoles().contains(ADMIN);

        if (!authorizationConfirmed) {
            throw new UserIsNotAuthorizedToThisQuizException("User is not authorized to this quiz.");
        }
    }

    private Object getParameterByName(JoinPoint joinPoint, String parameterName) {
        MethodSignature methodSig = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if (args.length==0){
            throw new UserIsNotAuthorizedToThisQuizException("Parameter " + parameterName + " not provided.");
        }
        String[] parametersName = methodSig.getParameterNames();
        int index = Arrays.asList(parametersName).indexOf(parameterName);
        if(index >= 0)
            return args[index];
        return null;
    }

}
