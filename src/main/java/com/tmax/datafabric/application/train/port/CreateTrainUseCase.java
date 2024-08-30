package com.tmax.datafabric.application.train.port;

import com.tmax.datafabric.application.train.port.dto.CreateTrainCommand;

public interface CreateTrainUseCase {

    void create(CreateTrainCommand createTrainCommand);
}
