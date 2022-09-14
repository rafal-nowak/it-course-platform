package pl.sages.javadevpro.projecttwo.api.quiz.solutiontemplate;//package pl.sages.javadevpro.projecttwo.api.quiz;

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
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizSolutionTemplateDto;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.PageQuizSolutionTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.api.quiz.mapper.QuizSolutionTemplateDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizSolutionTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/quiz-solution-templates",
        produces = "application/json",
        consumes = "application/json"
)
public class QuizSolutionTemplateController {

    private final QuizSolutionTemplateService quizSolutionTemplateService;
    private final QuizSolutionTemplateDtoMapper quizSolutionTemplateMapper;
    private final PageQuizSolutionTemplateDtoMapper pageQuizSolutionTemplateDtoMapper;

    @GetMapping
    public ResponseEntity<PageQuizSolutionTemplateDto> getQuizSolutionTemplates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageQuizSolutionTemplateDto pageQuizSolutionTemplates = pageQuizSolutionTemplateDtoMapper.toPageDto(quizSolutionTemplateService.findAll(pageable));

        return ResponseEntity.ok(pageQuizSolutionTemplates);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<QuizSolutionTemplateDto> getQuizSolutionTemplate(@PathVariable String id) {
        QuizSolutionTemplate quizSolutionTemplate = quizSolutionTemplateService.findById(id);
        return ResponseEntity
                .ok(quizSolutionTemplateMapper.toDto(quizSolutionTemplate));
    }

    @PostMapping
    public ResponseEntity<QuizSolutionTemplateDto> postQuizSolutionTemplate(@RequestBody QuizSolutionTemplateDto dto) {
        QuizSolutionTemplate quizSolutionTemplate = quizSolutionTemplateService.save(quizSolutionTemplateMapper.toDomain(dto));
        return ResponseEntity
                .ok(quizSolutionTemplateMapper.toDto(quizSolutionTemplate));
    }

    @PutMapping
    public ResponseEntity<Void> putQuizSolutionTemplate(@RequestBody QuizSolutionTemplateDto dto) {
        quizSolutionTemplateService.update(quizSolutionTemplateMapper.toDomain(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGradingTable(@PathVariable String id){
        quizSolutionTemplateService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
