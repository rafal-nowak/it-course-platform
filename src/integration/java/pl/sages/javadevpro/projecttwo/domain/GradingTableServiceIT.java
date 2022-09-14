package pl.sages.javadevpro.projecttwo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.GradingTableFactory;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableService;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GradingTableServiceIT extends BaseIT {

    @Autowired
    GradingTableService service;

    @Test
    void add_grading_table_test() {
        //given
        GradingTable gradingTable = GradingTableFactory.createRandom();
        service.save(gradingTable);
        //when
        GradingTable readGradingTable = service.findById(gradingTable.getTableId());
        //then
        compareGradingTables(gradingTable, readGradingTable);
    }

    @Test
    void get_id_should_return_correct_grading_table() {
        //given
        GradingTable gradingTable1 = GradingTableFactory.createRandom();
        GradingTable gradingTable2 = GradingTableFactory.createRandom();
        GradingTable gradingTable3 = GradingTableFactory.createRandom();
        service.save(gradingTable1);
        service.save(gradingTable2);
        service.save(gradingTable3);
        //when
        GradingTable readGradingTable = service.findById(gradingTable2.getTableId());
        //then
        compareGradingTables(gradingTable2, readGradingTable);
    }

    private void compareGradingTables(GradingTable model, GradingTable tested) {
        assertNotNull(tested);
        assertEquals(model.getTableId(), tested.getTableId());
        assertEquals(model.getTableName(), tested.getTableName());
        Assertions.assertIterableEquals(model.getRecords(), tested.getRecords());
    }
}
