package pl.sages.javadevpro.projecttwo.api.quiz.copy;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizControllerCommand;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizStatusDto;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.PageQuizDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.QuizDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.verification.AuthVerifyQuiz;
import pl.sages.javadevpro.projecttwo.api.task.verification.AuthVerifyTask;
import pl.sages.javadevpro.projecttwo.api.usertask.MessageResponse;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizCommand;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizService;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;
import pl.sages.javadevpro.projecttwo.security.UserPrincipal;

import static pl.sages.javadevpro.projecttwo.domain.user.model.UserRole.ADMIN;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final QuizDtoMapper quizMapper;
    private final PageQuizDtoMapper pageQuizDtoMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<PageQuizDto> getQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        Pageable pageable = PageRequest.of(page, size);

        PageQuizDto pageQuizzes;

        if (user.getRoles().contains(ADMIN)) {
            pageQuizzes = pageQuizDtoMapper.toPageDto(quizService.findAll(pageable));
        } else {
            pageQuizzes = pageQuizDtoMapper.toPageDto(quizService.findAllByUserId(pageable, user.getId()));
        }

        return ResponseEntity.ok(pageQuizzes);
    }

    @GetMapping(path = "/{quizId}")
    @AuthVerifyQuiz
    public ResponseEntity<QuizDto> getQuiz(@PathVariable String quizId) {
        Quiz quiz = quizService.findById(quizId);
        return ResponseEntity
                .ok(quizMapper.toDto(quiz));
    }

    @PostMapping
    public ResponseEntity<QuizDto> postQuiz(@RequestBody QuizDto dto) {
        Quiz quiz = quizService.save(quizMapper.toDomain(dto));
        return ResponseEntity
                .ok(quizMapper.toDto(quiz));
    }

    @PutMapping
    @AuthVerifyQuiz
    public ResponseEntity<Void> patchQuiz(@RequestBody QuizDto quizDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        if (user.getRoles().contains(ADMIN)) {
            quizService.update(quizMapper.toDomain(quizDto));
            return ResponseEntity.ok().build();
        } else if (quizService.findById(quizDto.getTestId()).getStatus().equals(QuizStatus.ASSIGNED)  && quizDto.getStatus().equals(QuizStatusDto.SUBMITTED)) {
            quizService.update(quizMapper.toDomain(quizDto));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeQuiz(@PathVariable String id){
        quizService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "{quizId}/commands")
    @AuthVerifyQuiz
    public ResponseEntity<MessageResponse> quizCommand(
            @PathVariable String quizId,
            @RequestBody QuizControllerCommand quizControllerCommand
    ) {
        String status = quizService.executeCommand(
                QuizCommand.valueOf(quizControllerCommand.getCommandName().name()),
                quizId
        );
        return ResponseEntity.ok(
                new MessageResponse("Quiz " + quizId
                        + " executed command: " + quizControllerCommand.getCommandName().name()
                        +  " status: " + status)
        );
    }

}
