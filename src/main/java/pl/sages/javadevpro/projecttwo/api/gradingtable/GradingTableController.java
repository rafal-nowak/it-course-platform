package pl.sages.javadevpro.projecttwo.api.gradingtable;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.GradingTableDto;
import pl.sages.javadevpro.projecttwo.api.gradingtable.dto.PageGradingTableDto;
import pl.sages.javadevpro.projecttwo.api.gradingtable.mapper.GradingTableDtoMapper;
import pl.sages.javadevpro.projecttwo.api.gradingtable.mapper.PageGradingTableDtoMapper;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableService;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/grading-tables")
public class GradingTableController {

    private final GradingTableService gradingTableService;
    private final GradingTableDtoMapper gradingTableDtoMapper;
    private final PageGradingTableDtoMapper pageGradingTableDtoMapper;

    @GetMapping( path = "/{id}")
    public ResponseEntity<GradingTableDto> getGradingTable(@PathVariable String id) {
        GradingTable gradingTable = gradingTableService.findById(id);
        return ResponseEntity
                .ok(gradingTableDtoMapper.toDto(gradingTable));
    }

    @GetMapping
    public ResponseEntity<PageGradingTableDto> getGradingTables(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageGradingTableDto pageGradingTables = pageGradingTableDtoMapper.toPageDto(gradingTableService.findAll(pageable));

        return ResponseEntity.ok(pageGradingTables);
    }

    @PostMapping
    public ResponseEntity<GradingTableDto> saveGradingTable(@RequestBody GradingTableDto dto) {
        GradingTable gradingTable = gradingTableService.save(gradingTableDtoMapper.toDomain(dto));
        return ResponseEntity
                .ok(gradingTableDtoMapper.toDto(gradingTable));
    }

    @PutMapping
    public ResponseEntity<Void> updateGradingTable(@RequestBody GradingTableDto dto) {
        gradingTableService.update(gradingTableDtoMapper.toDomain(dto));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGradingTable(@PathVariable String id){
        gradingTableService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
