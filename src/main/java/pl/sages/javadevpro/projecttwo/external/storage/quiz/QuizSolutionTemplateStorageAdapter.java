package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizSolutionTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class QuizSolutionTemplateStorageAdapter implements QuizSolutionTemplateRepository {

    private final MongoQuizSolutionTemplateRepository quizSolutionTemplateRepository;
    private final QuizSolutionTemplateEntityMapper mapper;

    @Override
    public QuizSolutionTemplate save(QuizSolutionTemplate quizSolutionTemplate) {
        try{
            QuizSolutionTemplateEntity saved = quizSolutionTemplateRepository.insert(mapper.toEntity(quizSolutionTemplate));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        }catch (DuplicateKeyException ex){
            log.warning("Quiz solution template " +  quizSolutionTemplate.getTestName() + " already exits");
            throw new QuizSolutionTemplateAlreadyExistsException();
        }
    }

    @Override
    public void update(QuizSolutionTemplate quizSolutionTemplate) {
        quizSolutionTemplateRepository.findByTestTemplateId(quizSolutionTemplate.getTestTemplateId()).ifPresent(quizSolutionTemplateEntity -> quizSolutionTemplateRepository.save(mapper.toEntity(quizSolutionTemplate)));
    }

    @Override
    public void remove(String id) {
        quizSolutionTemplateRepository.findById(id).ifPresent(quizSolutionTemplateEntity -> quizSolutionTemplateRepository.deleteById(id));
    }

    @Override
    public Optional<QuizSolutionTemplate> findById(String id) {
        return quizSolutionTemplateRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageQuizSolutionTemplate findAll(Pageable pageable) {
        Page<QuizSolutionTemplateEntity> pageOfQuizSolutionTemplateEntity = quizSolutionTemplateRepository.findAll(pageable);
        List<QuizSolutionTemplate> quizSolutionTemplatesOnCurrentPage = pageOfQuizSolutionTemplateEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageQuizSolutionTemplate(
                quizSolutionTemplatesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfQuizSolutionTemplateEntity.getTotalPages(),
                pageOfQuizSolutionTemplateEntity.getTotalElements()
        );
    }

    @Override
    public Optional<QuizSolutionTemplate> findByQuizTemplateId(String id) {
        return quizSolutionTemplateRepository.findByTestTemplateId(id).map(mapper::toDomain);
    }
}
