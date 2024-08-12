package com.example.datafabric.application.train.port;

import com.example.datafabric.application.train.port.dto.CreateTrainCommand;

public interface CreateTrainUseCase {

    void create(CreateTrainCommand createTrainCommand);
}
