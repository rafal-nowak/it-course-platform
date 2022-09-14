package pl.sages.javadevpro.projecttwo.api.quiz.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.PageQuizTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.QuizTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.verification.AuthVerifyQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/quiz-templates",
        produces = "application/json",
        consumes = "application/json"
)
public class QuizTemplateController {

    private final QuizTemplateService quizTemplateService;
    private final QuizTemplateDtoMapper quizTemplateMapper;
    private final PageQuizTemplateDtoMapper pageQuizTemplateDtoMapper;

    @GetMapping
    public ResponseEntity<PageQuizTemplateDto> getQuizTemplates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageQuizTemplateDto pageQuizTemplates = pageQuizTemplateDtoMapper.toPageDto(quizTemplateService.findAll(pageable));

        return ResponseEntity.ok(pageQuizTemplates);
    }

    @GetMapping(path = "/{quizTemplateId}")
    @AuthVerifyQuizTemplate
    public ResponseEntity<QuizTemplateDto> getQuizTemplate(@PathVariable String quizTemplateId) {
        QuizTemplate quizTemplate = quizTemplateService.findById(quizTemplateId);
        return ResponseEntity
                .ok(quizTemplateMapper.toDto(quizTemplate));
    }

    @PostMapping
    public ResponseEntity<QuizTemplateDto> postQuizTemplate(@RequestBody QuizTemplateDto dto) {
        QuizTemplate quizTemplate = quizTemplateService.save(quizTemplateMapper.toDomain(dto));
        return ResponseEntity
                .ok(quizTemplateMapper.toDto(quizTemplate));
    }

    @PutMapping
    public ResponseEntity<Void> putQuizTemplate(@RequestBody QuizTemplateDto dto) {
        quizTemplateService.update(quizTemplateMapper.toDomain(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeQuizTemplate(@PathVariable String id){
        quizTemplateService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
