package pl.sages.javadevpro.projecttwo.api.gradingtable.mapper;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.GradingTableDto;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;

@Mapper(componentModel = "spring")
public interface GradingTableDtoMapper {
    GradingTableDto toDto(GradingTable domain);

    GradingTable toDomain(GradingTableDto dto);
}