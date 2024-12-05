package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.CreateAnalysisUseCase;
import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;
import com.tmax.datafabric.application.analysis.port.dto.CreateAnalysisCommand;
import com.tmax.datafabric.application.analysis.port.dto.CreateAnalysisCommand.ResourceSpecDto;
import com.tmax.datafabric.application.config.ImageConfig;
import com.tmax.datafabric.domain.analysis.ResourceSpec;
import com.tmax.datafabric.domain.event.EventPublisher;
import com.tmax.datafabric.domain.event.AnalysisCreatedEvent;
import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateAnalysisService implements CreateAnalysisUseCase {
    private final AnalysisRepository analysisRepository;
    private final ImageConfig imageConfig;
    private final EventPublisher eventPublisher;

    @Override
    public AnalysisData create(CreateAnalysisCommand command) {
        //1. DB에 저장
        ResourceSpec resourceSpec = ResourceSpecDto.to(command.getResourceSpecDto());

        String datasourceType = Optional.ofNullable(command.getDatasourceType()).orElse("MinIO");

        Analysis analysis = Analysis.createAnalysis(command.getName(), datasourceType, command.getInputDataPath(),
                command.getSolutionType(), resourceSpec, command.getCreator());

        Analysis savedAnalysis = analysisRepository.save(analysis);

        //2. AnalysisCreatedEvent 발행
        eventPublisher.publish(AnalysisCreatedEvent.create(savedAnalysis.getId(), savedAnalysis.getDatasourceType(),
            savedAnalysis.getInputDataPath(), savedAnalysis.getSolutionType(),
            imageConfig.getAnalysisImageName(), resourceSpec.getCpuSize(), resourceSpec.getMemorySize()));

        return AnalysisData.from(savedAnalysis);
    }
}
