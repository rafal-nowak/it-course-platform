package pl.sages.javadevpro.projecttwo.domain.gradingtable.model;

import lombok.Value;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.GradingTableDto;

import java.util.List;

@Value
public class PageGradingTable {

    List<GradingTable> gradingTables;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}