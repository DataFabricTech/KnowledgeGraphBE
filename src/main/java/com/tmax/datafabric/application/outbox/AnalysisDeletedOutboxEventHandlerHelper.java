package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.application.config.KubernetesLabelConst;
import com.tmax.datafabric.domain.event.AnalysisCompletedEvent;
import com.tmax.datafabric.domain.event.AnalysisDeletedEvent;
import com.tmax.datafabric.domain.event.Event;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.pod.KubernetesPodClient;
import com.tmax.datafabric.kubernetesclient.workflow.KubernetesWorkflowClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalysisDeletedOutboxEventHandlerHelper implements OutboxEventHandlerHelper {
    private final KubernetesWorkflowClient workflowClient;
    private final KubernetesPodClient podClient;
    private final KubernetesConfig kubernetesConfig;

    @Override
    public boolean support(OutboxEvent outboxEvent) {
        return outboxEvent.getAggregateType().equals(AggregateType.DATAFABRIC_ANALYSIS) &&
            outboxEvent.getEventType().equals(AnalysisDeletedEvent.class.getName());
    }

    @Override
    public void handle(Event domainEvent) {
        AnalysisCompletedEvent analysisCompletedEvent = (AnalysisCompletedEvent) domainEvent;

        Long analysisId = analysisCompletedEvent.getAnalysisId();

        deleteAnalysisWorkflow(analysisId);
        deleteAnalysisResult(analysisId);

        log.info("[WORKFLOW NOTIFICATION] Analysis (analysisId = {}) Deleted.", analysisId);
    }

    private void deleteAnalysisWorkflow(Long analysisId) {
        workflowClient.deleteWorkflow(kubernetesConfig.getNamespace(),
            String.format("workflow-%s-%d", KubernetesLabelConst.ANALYSIS, analysisId));
    }

    private void deleteAnalysisResult(Long analysisId) {
        String deleteDirectory = String.format("%s/analysis-%d", kubernetesConfig.getMountPath(),
            analysisId);
        String[] deleteCommands = new String[]{"rm", "-rf", deleteDirectory};

        podClient.execPodAsync(kubernetesConfig.getNamespace(), kubernetesConfig.getPodName(),
            deleteCommands);
    }
}
