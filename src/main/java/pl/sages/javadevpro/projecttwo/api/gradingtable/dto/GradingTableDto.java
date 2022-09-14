package pl.sages.javadevpro.projecttwo.api.gradingtable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class GradingTableDto {
    String tableId;
    String tableName;
    List<GradingRecordDto> records;
}
