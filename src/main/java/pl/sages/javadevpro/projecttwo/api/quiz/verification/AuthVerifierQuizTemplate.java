package pl.sages.javadevpro.projecttwo.api.quiz.verification;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizService;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;
import pl.sages.javadevpro.projecttwo.security.UserPrincipal;

import java.util.Arrays;

import static pl.sages.javadevpro.projecttwo.domain.user.model.UserRole.ADMIN;

@Component
@Aspect
@RequiredArgsConstructor
class AuthVerifierQuizTemplate {

    private final UserService userService;
    private final QuizService quizService;


    @Before(value = "@annotation(authVerifyQuizTemplate)")
    public void userIsAdminOrHasTaskCreatedFromTestTemplateWithId(JoinPoint joinPoint, AuthVerifyQuizTemplate authVerifyQuizTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String quizTemplateId = (String) getParameterByName(joinPoint, authVerifyQuizTemplate.quizTemplateIdParamName());

        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());
        boolean authorizationConfirmed = quizService.hasUserQuizCreatedFromQuizTemplateWithId(user.getId(), quizTemplateId) || user.getRoles().contains(ADMIN);

        if (!authorizationConfirmed) {
            throw new UserIsNotAuthorizedToThisQuizTemplateException("User is not authorized to this quiz template.");
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
