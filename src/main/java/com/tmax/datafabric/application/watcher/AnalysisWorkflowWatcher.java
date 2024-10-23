package com.tmax.datafabric.application.watcher;

import com.tmax.datafabric.application.analysis.port.CompleteAnalysisUseCase;
import com.tmax.datafabric.application.analysis.port.FailAnalysisUseCase;
import com.tmax.datafabric.application.analysis.port.RunningAnalysisUseCase;
import com.tmax.datafabric.application.config.KubernetesLabelConst;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.NodeStatus;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Workflow;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.WorkflowStatus;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalysisWorkflowWatcher {
    private final KubernetesClient kubernetesClient;
    private final TransactionTemplate transactionTemplate;
    private final AnalysisRepository analysisRepository;
    private final RunningAnalysisUseCase runningAnalysisUseCase;
    private final FailAnalysisUseCase failAnalysisUseCase;
    private final CompleteAnalysisUseCase completeAnalysisUseCase;
    private final KubernetesConfig kubernetesConfig;

    @PostConstruct
    private void watchAnalysisWorkflow() {
        kubernetesClient.resources(Workflow.class).inNamespace(kubernetesConfig.getNamespace())
            .withLabels(KubernetesLabelConst.DEFAULT_ANALYSIS_LABELS)
            .withLabel(KubernetesLabelConst.ANALYSIS_ID_KEY)
            .watch(createWorkflowWatcher());
    }

    private Watcher<Workflow> createWorkflowWatcher() {
        return new Watcher<Workflow>() {

            @Override
            public void eventReceived(Action action, Workflow workflow) {
                WorkflowStatus workflowStatus = workflow.getStatus();

                if (workflowStatus == null || workflowStatus.getPhase() == null
                    || action.equals(Action.DELETED)) {
                    return;
                }

                Map<String, String> labels = workflow.getMetadata().getLabels();
                Long analysisId = Long.valueOf(labels.get(KubernetesLabelConst.ANALYSIS_ID_KEY));

                switch (workflowStatus.getPhase().toLowerCase()){
                    case WorkflowPhase.RUNNING:
                        for (String key: workflowStatus.getNodes().keySet()) {
                            if (workflowStatus.getNodes().get(key).getPhase().equalsIgnoreCase("pending")
                                && workflowStatus.getNodes().get(key).getMessage() != null
                                && workflowStatus.getNodes().get(key).getMessage().contains("ImagePullBackOff")) {
                                handleFailedAnalysisWorkflow(analysisId);
                                break;
                            }
                        }

                        if (analysisRepository.findById(analysisId).isPresent()) {
                            return;
                        }

                        handleRunningAnalysisWorkflow(analysisId);
                        break;
                    case WorkflowPhase.SUCCEEDED:
                        handleCompleteAnalysisWorkflow(analysisId);
                        break;
                    case WorkflowPhase.FAILED:
                        handleFailedAnalysisWorkflow(analysisId);
                        break;
                }
            }

            private void handleRunningAnalysisWorkflow(Long analysisId) {
                transactionTemplate.executeWithoutResult(action -> {
                    runningAnalysisUseCase.running(analysisId);
                });
            }

            private void handleCompleteAnalysisWorkflow(Long analysisId) {
                transactionTemplate.executeWithoutResult(action -> {
                    completeAnalysisUseCase.complete(analysisId);
                });
            }

            private void handleFailedAnalysisWorkflow(Long analysisId) {
                transactionTemplate.executeWithoutResult(action -> {
                    failAnalysisUseCase.fail(analysisId);
                });
            }

            @Override
            public void onClose(WatcherException e) {
                log.info("[WATCHER WARNING] workflow watcher closed\n Cause : {}", e.toString());
                kubernetesClient.resources(Workflow.class).inAnyNamespace().watch(createWorkflowWatcher());
                log.info("[WATCHER NOTIFICATION] workflow watcher created");
            }
        };
    }
}
