package com.example.datastructure.client;

import com.example.datastructure.application.CreateDataStructureUseCase;
import com.example.datastructure.application.CreateIndexUseCase;
import com.example.datastructure.application.DeleteIndexUseCase;
import com.example.datastructure.application.FindDataStructureUseCase;
import com.example.datastructure.client.dto.CreateDataStructureCommandDTO;
import com.example.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.datastructure.client.dto.DataStructureDTO;
import com.example.datastructure.client.dto.DeleteIndexCommandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicesrest/data-structure")
public class DataStructureController {

    private final CreateDataStructureUseCase createDataStructureUseCase;
    private final FindDataStructureUseCase findDataStructureUseCase;
    private final CreateIndexUseCase createIndexUseCase;
    private final DeleteIndexUseCase deleteIndexUseCase;

    @GetMapping("/{dataStructureName}")
    public DataStructureDTO findDataStructureByName(@PathVariable String dataStructureName) {
        return findDataStructureUseCase.findByName(dataStructureName);
    }

    @PostMapping
    public void createDataStructure(@RequestBody CreateDataStructureCommandDTO command) {
        createDataStructureUseCase.create(command);
    }

    @PostMapping("/index")
    public void createIndexForDataStructure(@RequestBody CreateIndexCommandDTO command) {
        createIndexUseCase.createForDataStructure(command);
    }

    @PostMapping("/index/delete")
    public void deleteIndexForDataStructure(@RequestBody DeleteIndexCommandDTO command) {
        deleteIndexUseCase.delete(command);
    }
}
