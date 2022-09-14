package pl.sages.javadevpro.projecttwo.api.gradingtable.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.GradingTableDto;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.PageGradingTableDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.PageQuizDto;
import pl.sages.javadevpro.projecttwo.api.quiz.dto.QuizDto;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.PageGradingTable;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageGradingTableDtoMapper {

    @Mapping(target = "gradingTables", qualifiedByName = "toGradingTableDtoList")
    PageGradingTableDto toPageDto(PageGradingTable domain);

    @Named("toGradingTableDtoList")
    @IterableMapping(qualifiedByName = "gradingTableToGradingTableDto")
    List<GradingTableDto> toListDto(List<GradingTable> gradingTables);

    @Named("gradingTableToGradingTableDto")
    GradingTableDto toDto(GradingTable domain);
}