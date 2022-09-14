package pl.sages.javadevpro.projecttwo.external.storage.gradingtable;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableRepository;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.PageGradingTable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizEntity;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintAlreadyExistsException;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class GradingTableStorageAdapter implements GradingTableRepository {

    private final MongoGradingTableRepository gradingTableRepository;
    private final GradingTableEntityMapper mapper;

    @Override
    public GradingTable save(GradingTable gradingTable) {
        try{
            GradingTableEntity saved = gradingTableRepository.insert(mapper.toEntity(gradingTable));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        }catch (DuplicateKeyException ex){
            log.warning("Grading table " +  gradingTable.getTableName() + " already exits");
            throw new TaskBlueprintAlreadyExistsException();
        }
    }

    @Override
    public void update(GradingTable gradingTable) {
        gradingTableRepository.findById(gradingTable.getTableId()).ifPresent(quizEntity -> gradingTableRepository.save(mapper.toEntity(gradingTable)));
    }

    @Override
    public void remove(String id) {
        gradingTableRepository.findById(id).ifPresent(quizEntity -> gradingTableRepository.deleteById(id));
    }

    @Override
    public Optional<GradingTable> findById(String id) {
        return gradingTableRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageGradingTable findAll(Pageable pageable) {
        Page<GradingTableEntity> pageOfGradingTablesEntity = gradingTableRepository.findAll(pageable);
        List<GradingTable> gradingTablesOnCurrentPage = pageOfGradingTablesEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageGradingTable(
                gradingTablesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfGradingTablesEntity.getTotalPages(),
                pageOfGradingTablesEntity.getTotalElements()
        );
    }
}
