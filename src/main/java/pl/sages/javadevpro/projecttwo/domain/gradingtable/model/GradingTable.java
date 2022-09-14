package pl.sages.javadevpro.projecttwo.domain.gradingtable.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.GradingRecordDto;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class GradingTable {
    String tableId;
    String tableName;
    List<GradingRecord> records;
}
