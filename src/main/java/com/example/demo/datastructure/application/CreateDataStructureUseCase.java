package com.example.demo.datastructure.application;

import com.example.demo.datastructure.infrastructure.DataStructureOracleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDataStructureUseCase {

    private final DataStructureOracleRepositoryPort dataStructureOracleRepositoryPort;

}
