package pl.sages.javadevpro.projecttwo;

import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingRecord;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;

import java.util.List;

public class GradingTableFactory {

    private static int sequence = 0;

    public static GradingTable createRandom() {
        sequence++;
        return new GradingTable(
                "TEST " + sequence,
                "Table Name " + sequence,
                List.of(
                        new GradingRecord(
                                0,
                                50,
                                "1"
                        ),
                        new GradingRecord(
                                51,
                                60,
                                "2"
                        ),
                        new GradingRecord(
                                61,
                                70,
                                "3"
                        ),
                        new GradingRecord(
                                71,
                                80,
                                "4"
                        ),
                        new GradingRecord(
                                81,
                                90,
                                "5"
                        ),
                        new GradingRecord(
                                91,
                                100,
                                "6"
                        )
                )
        );
    }
}
