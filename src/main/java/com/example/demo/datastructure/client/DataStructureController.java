package com.example.demo.datastructure.client;

import com.example.demo.datastructure.application.CreateDataStructureUseCase;
import com.example.demo.datastructure.application.FindDataStructureUseCase;
import com.example.demo.datastructure.client.dto.CreateDataStructureCommandDTO;
import com.example.demo.datastructure.client.dto.DataStructureDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicesrest/data-structure")
public class DataStructureController {

    private final CreateDataStructureUseCase createDataStructureUseCase;
    private final FindDataStructureUseCase findDataStructureUseCase;

    @GetMapping("/{dataStructureName}")
    public DataStructureDTO findDataStructureByName(@PathVariable String dataStructureName) {
        return findDataStructureUseCase.findByName(dataStructureName);
    }

    @PostMapping
    public void findDataStructureByName(@RequestBody CreateDataStructureCommandDTO command) {
        createDataStructureUseCase.create(command);
    }

}
