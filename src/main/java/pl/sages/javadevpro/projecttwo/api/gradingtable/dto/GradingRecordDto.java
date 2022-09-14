package pl.sages.javadevpro.projecttwo.api.gradingtable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class GradingRecordDto {
    Integer lowerLimit;
    Integer upperLimit;
    String grade;
}
