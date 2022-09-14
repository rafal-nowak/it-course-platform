package pl.sages.javadevpro.projecttwo.domain.gradingtable;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.exception.GradingTableNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.PageGradingTable;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;

@RequiredArgsConstructor
public class GradingTableService {

    private final GradingTableRepository gradingTableRepository;

    public GradingTable save(GradingTable gradingTable) {
        return gradingTableRepository.save(gradingTable);
    }

    public void update(GradingTable gradingTable) {
        gradingTableRepository.update(gradingTable);
    }

    public void removeById(String id) {
        gradingTableRepository.remove(id);
    }

    public GradingTable findById(String id) {
        return gradingTableRepository.findById(id)
                .orElseThrow(GradingTableNotFoundException::new);
    }

    public PageGradingTable findAll(Pageable pageable) {
        return gradingTableRepository.findAll(pageable);
    }

}
