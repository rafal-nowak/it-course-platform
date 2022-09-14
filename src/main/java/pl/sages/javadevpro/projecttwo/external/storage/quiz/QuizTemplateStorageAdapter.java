package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.GradingTableEntity;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class QuizTemplateStorageAdapter implements QuizTemplateRepository {

    private final MongoQuizTemplateRepository quizTemplateRepository;
    private final QuizTemplateEntityMapper mapper;

    @Override
    public QuizTemplate save(QuizTemplate quizTemplate) {
        try{
            QuizTemplateEntity saved = quizTemplateRepository.insert(mapper.toEntity(quizTemplate));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        }catch (DuplicateKeyException ex){
            log.warning("Quiz template " +  quizTemplate.getTestName() + " already exits");
            throw new QuizTemplateAlreadyExistsException();
        }
    }

    @Override
    public void update(QuizTemplate quizTemplate) {
        quizTemplateRepository.findById(quizTemplate.getTestTemplateId()).ifPresent(quizTemplateEntity -> quizTemplateRepository.save(mapper.toEntity(quizTemplate)));
    }

    @Override
    public void remove(String id) {
        quizTemplateRepository.findById(id).ifPresent(quizTemplateEntity -> quizTemplateRepository.deleteById(id));
    }

    @Override
    public Optional<QuizTemplate> findById(String id) {
        return quizTemplateRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageQuizTemplate findAll(Pageable pageable) {
        Page<QuizTemplateEntity> pageOfQuizTemplateEntity = quizTemplateRepository.findAll(pageable);
        List<QuizTemplate> quizTemplatesOnCurrentPage = pageOfQuizTemplateEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageQuizTemplate(
                quizTemplatesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfQuizTemplateEntity.getTotalPages(),
                pageOfQuizTemplateEntity.getTotalElements()
        );
    }
}
