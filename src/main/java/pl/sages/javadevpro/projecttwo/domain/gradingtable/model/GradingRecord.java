package pl.sages.javadevpro.projecttwo.domain.gradingtable.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class GradingRecord {
    Integer lowerLimit;
    Integer upperLimit;
    String grade;
}
