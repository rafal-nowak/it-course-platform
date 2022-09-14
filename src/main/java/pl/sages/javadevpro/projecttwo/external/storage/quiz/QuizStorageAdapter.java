package pl.sages.javadevpro.projecttwo.external.storage.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuizTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class QuizStorageAdapter implements QuizRepository {

    private final MongoQuizRepository quizRepository;
    private final QuizEntityMapper mapper;

    @Override
    public Quiz save(Quiz quiz) {
        try{
            QuizEntity saved = quizRepository.insert(mapper.toEntity(quiz));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        }catch (DuplicateKeyException ex){
            log.warning("Quiz " +  quiz.getTestName() + " already exits");
            throw new QuizAlreadyExistsException();
        }
    }

    @Override
    public void update(Quiz quiz) {
        quizRepository.findById(quiz.getTestId()).ifPresent(quizEntity -> quizRepository.save(mapper.toEntity(quiz)));
    }

    @Override
    public void remove(String id) {
        quizRepository.findById(id).ifPresent(quizEntity -> quizRepository.deleteById(id));
    }

    @Override
    public Optional<Quiz> findById(String id) {
        return quizRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageQuiz findAll(Pageable pageable) {
        Page<QuizEntity> pageOfQuizEntity = quizRepository.findAll(pageable);
        List<Quiz> quizzesOnCurrentPage = pageOfQuizEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageQuiz(
                quizzesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfQuizEntity.getTotalPages(),
                pageOfQuizEntity.getTotalElements()
        );
    }

    @Override
    public PageQuiz findAllByUserId(Pageable pageable, String userId) {
        Page<QuizEntity> pageOfQuizEntity = quizRepository.findAllByUserId(pageable, userId);
        List<Quiz> quizzesOnCurrentPage = pageOfQuizEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageQuiz(
                quizzesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfQuizEntity.getTotalPages(),
                pageOfQuizEntity.getTotalElements()
        );
    }

    @Override
    public List<Quiz> findAllByTestTemplateIdAndUserId(String testTemplateId, String userId) {
        return quizRepository.findAllByTestTemplateIdAndUserId(testTemplateId, userId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

}
