package pl.sages.javadevpro.projecttwo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.sages.javadevpro.projecttwo.BaseIT;
import pl.sages.javadevpro.projecttwo.GradingTableFactory;
import pl.sages.javadevpro.projecttwo.TestUserFactory;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.GradingTableDto;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.PageGradingTableDto;
import pl.sages.javadevpro.projecttwo.api.gradingtable.mapper.GradingTableDtoMapper;
import pl.sages.javadevpro.projecttwo.api.gradingtable.mapper.PageGradingTableDtoMapper;
import pl.sages.javadevpro.projecttwo.api.task.blueprint.TaskBlueprintDto;
import pl.sages.javadevpro.projecttwo.api.usertask.ErrorResponse;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableService;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingRecord;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.domain.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GradingTableControllerIT extends BaseIT {

    @Autowired
    private GradingTableService gradingTableService;
    @Autowired
    private GradingTableDtoMapper gradingTableDtoMapper;
    @Autowired
    private PageGradingTableDtoMapper pageGradingTableDtoMapper;
    @Autowired
    UserService userService;

    @Test
    void should_get_information_about_grading_table() {
        //given
        User user = TestUserFactory.createStudent();
        GradingTable gradingTable = GradingTableFactory.createRandom();
        userService.save(user);
        gradingTableService.save(gradingTable);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/grading-tables/" + gradingTable.getTableId(),
                token,
                null,
                GradingTableDto.class);

        //then
        GradingTableDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareGradingTables(gradingTable, gradingTableDtoMapper.toDomain(body));
    }

    @Test
    void should_get_information_about_correct_grading_table() {
        //given
        User user = TestUserFactory.createStudent();
        GradingTable gradingTable1 = GradingTableFactory.createRandom();
        GradingTable gradingTable2 = GradingTableFactory.createRandom();
        GradingTable gradingTable3 = GradingTableFactory.createRandom();
        userService.save(user);
        gradingTableService.save(gradingTable1);
        gradingTableService.save(gradingTable2);
        gradingTableService.save(gradingTable3);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/grading-tables/" + gradingTable2.getTableId(),
                token,
                null,
                GradingTableDto.class);

        //then
        GradingTableDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareGradingTables(gradingTable2, gradingTableDtoMapper.toDomain(body));
    }

    @Test
    void should_get_information_about_all_grading_tables() {
        //given
        User user = TestUserFactory.createStudent();
        GradingTable gradingTable1 = GradingTableFactory.createRandom();
        GradingTable gradingTable2 = GradingTableFactory.createRandom();
        GradingTable gradingTable3 = GradingTableFactory.createRandom();
        userService.save(user);
        gradingTableService.save(gradingTable1);
        gradingTableService.save(gradingTable2);
        gradingTableService.save(gradingTable3);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/grading-tables",
                token,
                null,
                PageGradingTableDto.class);

        //then
        PageGradingTableDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        compareGradingTables(gradingTable1, gradingTableDtoMapper.toDomain(body.getGradingTables().get(0)));
        compareGradingTables(gradingTable2, gradingTableDtoMapper.toDomain(body.getGradingTables().get(1)));
        compareGradingTables(gradingTable3, gradingTableDtoMapper.toDomain(body.getGradingTables().get(2)));
    }

    @Test
    void admin_should_be_able_to_save_new_grading_table() {
        //given
        GradingTable gradingTable = GradingTableFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/grading-tables",
                adminAccessToken,
                gradingTableDtoMapper.toDto(gradingTable),
                GradingTableDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        GradingTableDto body = response.getBody();
        //and
        compareGradingTables(gradingTable, gradingTableDtoMapper.toDomain(body));
    }

    @Test
    void student_should_not_be_able_to_save_new_grading_table() {
        //given
        User user = TestUserFactory.createStudent();
        GradingTable gradingTable = GradingTableFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/grading-tables",
                token,
                gradingTableDtoMapper.toDto(gradingTable),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void should_return_conflict_about_duplicated_grading_table(){
        //given
        GradingTable gradingTable = GradingTableFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        gradingTableService.save(gradingTable);
        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/grading-tables",
                adminAccessToken,
                gradingTableDtoMapper.toDto(gradingTable),
                ErrorResponse.class);
        //then
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_grading_table() {
        //given
        GradingTable gradingTable = GradingTableFactory.createRandom();
        String adminAccessToken = getTokenForAdmin();
        gradingTableService.save(gradingTable);
        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/grading-tables/" + gradingTable.getTableId(),
                adminAccessToken,
                null,
                Void.class);
        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void student_should_not_be_able_to_delete_grading_table() {
        //given
        User user = TestUserFactory.createStudent();
        GradingTable gradingTable = GradingTableFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        gradingTableService.save(gradingTable);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/grading-tables/" + gradingTable.getTableId(),
                token,
                null,
                ErrorResponse.class
        );

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_update_grading_table() {
        //given
        GradingTable gradingTable = GradingTableFactory.createRandom();
        GradingTable updatedGradingTable = new GradingTable(
                gradingTable.getTableId(),
                "Table Name is updated",
                List.of(
                        new GradingRecord(
                                0,
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
                                100,
                                "5"
                        )
                )
        );
        String adminAccessToken = getTokenForAdmin();
        gradingTableService.save(gradingTable);

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/grading-tables",
                adminAccessToken,
                gradingTableDtoMapper.toDto(updatedGradingTable),
                TaskBlueprintDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        TaskBlueprintDto body = response.getBody();
        Assertions.assertNull(body);
    }

    @Test
    void should_get_response_code_404_when_grading_table_not_exits() {
        //given
        User user = TestUserFactory.createStudent();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/grading-tables/1",
                token,
                null,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private void compareGradingTables(GradingTable model, GradingTable tested) {
        assertNotNull(tested);
        assertEquals(model.getTableId(), tested.getTableId());
        assertEquals(model.getTableName(), tested.getTableName());
        Assertions.assertIterableEquals(model.getRecords(), tested.getRecords());
    }
}
