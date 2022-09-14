package pl.sages.javadevpro.projecttwo.domain.gradingtable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.exception.GradingTableNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingRecord;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.GradingTableAlreadyExistsException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GradingTableServiceTest {

    @Mock
    private GradingTableRepository gradingTableRepository;

    @InjectMocks
    private GradingTableService gradingTableService;

    private final GradingTable fakeGradingTable = new GradingTable(
            "GDID123",
            "table name",
            List.of(
                    new GradingRecord(
                            0,
                            50,
                            "1"
                    ),
                    new GradingRecord(
                            51,
                            60,
                            "1"
                    ),
                    new GradingRecord(
                            61,
                            70,
                            "1"
                    ),
                    new GradingRecord(
                            71,
                            80,
                            "1"
                    ),
                    new GradingRecord(
                            81,
                            90,
                            "1"
                    ),
                    new GradingRecord(
                            91,
                            100,
                            "1"
                    )
            )
    );

    @Test
    void save_method_should_return_saved_grading_table_when_grading_table_does_not_exist() {
        Mockito.when(gradingTableRepository.save(fakeGradingTable)).thenReturn(fakeGradingTable);

        //when
        GradingTable savedGradingTable = gradingTableService.save(fakeGradingTable);

        //then
        compareGradingTables(fakeGradingTable, savedGradingTable);
    }

    @Test
    void save_method_should_throw_grading_table_already_exist_exception_when_grading_table_exist() {
        Mockito.when(gradingTableRepository.save(fakeGradingTable)).thenThrow(new GradingTableAlreadyExistsException());

        //when
        //then
        Assertions.assertThrows(GradingTableAlreadyExistsException.class,
                ()-> gradingTableService.save(fakeGradingTable));
    }

    @Test
    void find_by_id_method_should_return_founded_grading_table_when_grading_table_exist() {
        Mockito.when(gradingTableRepository.findById(fakeGradingTable.getTableId())).thenReturn(Optional.of(fakeGradingTable));

        //when
        GradingTable foundedGradingTable = gradingTableService.findById(fakeGradingTable.getTableId());

        //then
        compareGradingTables(fakeGradingTable, foundedGradingTable);
    }

    @Test
    void find_by_id_method_should_throw_grading_table_not_found_exception_when_grading_table_does_not_exist() {
        Mockito.when(gradingTableRepository.findById(fakeGradingTable.getTableId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(GradingTableNotFoundException.class,
                ()-> gradingTableService.findById(fakeGradingTable.getTableId()));
    }

    @Test
    void remove_method_should_invoke_remove_method_from_grading_table_repository() {
        //when
        gradingTableService.removeById(fakeGradingTable.getTableId());
        //then
        Mockito.verify(gradingTableRepository).remove(fakeGradingTable.getTableId());
    }

    @Test
    void update_method_should_invoke_update_method_from_grading_table_repository() {
        //when
        gradingTableService.update(fakeGradingTable);
        //then
        Mockito.verify(gradingTableRepository).update(fakeGradingTable);
    }

    @Test
    void find_all_method_should_invoke_find_all_method_from_grading_table_repository() {
        //when
        gradingTableService.findAll(null);
        //then
        Mockito.verify(gradingTableRepository).findAll(null);
    }

    private void compareGradingTables(GradingTable model, GradingTable tested) {
        assertNotNull(tested);
        assertEquals(model.getTableId(), tested.getTableId());
        assertEquals(model.getTableName(), tested.getTableName());
        Assertions.assertIterableEquals(model.getRecords(), tested.getRecords());
    }

}