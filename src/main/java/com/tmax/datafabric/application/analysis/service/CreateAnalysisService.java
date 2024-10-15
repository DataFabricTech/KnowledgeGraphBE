package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.CreateAnalysisUseCase;
import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;
import com.tmax.datafabric.application.analysis.port.dto.CreateAnalysisCommand;
import com.tmax.datafabric.application.analysis.port.dto.CreateAnalysisCommand.HyperparameterDto;
import com.tmax.datafabric.application.analysis.port.dto.CreateAnalysisCommand.ResourceSpecDto;
import com.tmax.datafabric.application.config.ImageConfig;
import com.tmax.datafabric.domain.analysis.ResourceSpec;
import com.tmax.datafabric.domain.event.EventPublisher;
import com.tmax.datafabric.domain.event.AnalysisCreatedEvent;
import com.tmax.datafabric.domain.analysis.HyperParameter;
import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAnalysisService implements CreateAnalysisUseCase {
    private final AnalysisRepository analysisRepository;
    private final ImageConfig imageConfig;
    private final EventPublisher eventPublisher;

    @Override
    public AnalysisData create(CreateAnalysisCommand command) {
        //1. DB에 저장
        HyperParameter hyperParameter = HyperparameterDto.to(command.getHyperparameterDto());
        ResourceSpec resourceSpec = ResourceSpecDto.to(command.getResourceSpecDto());

        Analysis analysis = Analysis.createAnalysis(command.getName(), command.getInputDataPath(),
            command.getSolutionType(), hyperParameter, resourceSpec, command.getCreator());

        Analysis savedAnalysis = analysisRepository.save(analysis);

        //2. AnalysisCreatedEvent 발행
        eventPublisher.publish(AnalysisCreatedEvent.create(savedAnalysis.getId(),
            savedAnalysis.getInputDataPath(), savedAnalysis.getSolutionType(),
            imageConfig.getAnalysisImageName(), hyperParameter.getModelHyperparameters(),
            hyperParameter.getFeatureHyperparameters(), hyperParameter.getLearningHyperparameters(),
            resourceSpec.getCpuSize(), resourceSpec.getMemorySize()));

        return AnalysisData.from(savedAnalysis);
    }
}
