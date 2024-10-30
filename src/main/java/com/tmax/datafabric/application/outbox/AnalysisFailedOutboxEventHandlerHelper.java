package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.application.config.KubernetesLabelConst;
import com.tmax.datafabric.domain.event.AnalysisCompletedEvent;
import com.tmax.datafabric.domain.event.AnalysisFailedEvent;
import com.tmax.datafabric.domain.event.Event;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.workflow.KubernetesWorkflowClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalysisFailedOutboxEventHandlerHelper implements OutboxEventHandlerHelper {
    private final KubernetesWorkflowClient workflowClient;
    private final KubernetesConfig kubernetesConfig;

    @Override
    public boolean support(OutboxEvent outboxEvent) {
        return outboxEvent.getAggregateType().equals(AggregateType.DATAFABRIC_ANALYSIS) &&
            outboxEvent.getEventType().equals(AnalysisFailedEvent.class.getName());
    }

    @Override
    public void handle(Event domainEvent) {
        AnalysisFailedEvent analysisFailedEvent = (AnalysisFailedEvent) domainEvent;

        Long analysisId = analysisFailedEvent.getAnalysisId();

        deleteAnalysisWorkflow(analysisId);

        log.info("[WORKFLOW NOTIFICATION] Analysis (analysisId = {}) Failed.", analysisId);
    }

    private void deleteAnalysisWorkflow(Long analysisId) {
        workflowClient.deleteWorkflow(kubernetesConfig.getNamespace(),
            String.format("workflow-%s-%d", KubernetesLabelConst.ANALYSIS, analysisId));
    }
}
