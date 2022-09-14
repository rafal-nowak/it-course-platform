package pl.sages.javadevpro.projecttwo.api.gradingtable.dto;

import lombok.Value;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;

import java.util.List;

@Value
public class PageGradingTableDto {

    List<GradingTableDto> gradingTables;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}