package com.example.datafabric.interfaces.train;

import com.example.datafabric.application.train.port.CreateTrainUseCase;
import com.example.datafabric.application.train.port.dto.CreateTrainCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestTrainController
@RequiredArgsConstructor
public class CreateTrainController {

    private final CreateTrainUseCase createTrainUseCase;

    @PostMapping
    public void createTrain(
        @RequestBody CreateTrainCommand createTrainCommand) {
        createTrainUseCase.create(createTrainCommand);
    }
}
