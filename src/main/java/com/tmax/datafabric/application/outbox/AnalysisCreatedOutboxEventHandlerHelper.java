package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.application.config.DatafabricConst;
import com.tmax.datafabric.application.config.ImageConfig;
import com.tmax.datafabric.application.config.KubernetesLabelConst;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.workflow.KubernetesWorkflowClient;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.DagTask;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.DagTemplate;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Template;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Workflow;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.WorkflowSpec;
import com.tmax.datafabric.domain.event.Event;
import com.tmax.datafabric.domain.event.AnalysisCreatedEvent;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimVolumeSource;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeMount;
import io.fabric8.kubernetes.client.KubernetesClient;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisCreatedOutboxEventHandlerHelper implements
    com.tmax.datafabric.application.outbox.OutboxEventHandlerHelper {

    private final AnalysisRepository analysisRepository;
    private final ImageConfig imageConfig;
    private final KubernetesWorkflowClient workflowClient;
    private final KubernetesConfig kubernetesConfig;

    @Override
    public boolean support(OutboxEvent outboxEvent) {
        return outboxEvent.getAggregateType().equals(AggregateType.DATAFABRIC_ANALYSIS)
            && outboxEvent.getEventType().equals(AnalysisCreatedEvent.class.getName());
    }

    @Override
    public void handle(Event domainEvent) {

        AnalysisCreatedEvent analysisCreatedEvent = (AnalysisCreatedEvent) domainEvent;
        if (!analysisRepository.findById(analysisCreatedEvent.getAnalysisId()).isPresent()) {
            return;
        }

        createWorkflowFromAnalysisCreatedEvent(analysisCreatedEvent);
        log.info("[WORKFLOW NOTIFICATION] Analysis (analysisId = {}) Created.",
            analysisCreatedEvent.getAnalysisId());
    }

    private void createWorkflowFromAnalysisCreatedEvent(AnalysisCreatedEvent analysisCreatedEvent){
        List<String> command = Arrays.asList("python3", "agent/main.py");
        ContainerBuilder containerBuilder = new ContainerBuilder();

        Map<String, String> labels = new HashMap<>(KubernetesLabelConst.DEFAULT_ANALYSIS_LABELS);
        labels.put(KubernetesLabelConst.ANALYSIS_ID_KEY, String.valueOf(analysisCreatedEvent.getAnalysisId()));

        List<Template> templates = new ArrayList<Template>();

        ResourceRequirements resourceRequirements = new ResourceRequirements();
        Map<String, Quantity> resource = new HashMap<>();

        if (!(analysisCreatedEvent.getCpuSize().isEmpty())
            && !(analysisCreatedEvent.getMemorySize().isEmpty())) {
            resource.put("cpu", new Quantity(analysisCreatedEvent.getCpuSize()));
            resource.put("memory", new Quantity(analysisCreatedEvent.getMemorySize()));
        } else {
            resource.put("cpu", new Quantity("1"));
            resource.put("memory", new Quantity("1Gi"));
        }

        resourceRequirements.setLimits(resource);
        resourceRequirements.setRequests(resource);

        List<String> args = new ArrayList<>();

        args.add("--analysis_id");
        args.add(analysisCreatedEvent.getAnalysisId().toString());

        args.add("--mode");

        if (analysisCreatedEvent.getInputDataPath().isEmpty()) {
            args.add("mock");
        } else {
            args.add("production");

            args.add("--input_data_path");
            args.add(analysisCreatedEvent.getInputDataPath());
        }

        String analysisOutputCsvFilePath = DatafabricConst.DATAFABRIC_MOUNT_PATH + File.separator
            + String.format("analysis-%d", analysisCreatedEvent.getAnalysisId()) + File.separator
            + "association_rule_result.csv";

        args.add("--output_data_path");
        args.add(analysisOutputCsvFilePath);

        args.add("--algorithm");
        args.add(analysisCreatedEvent.getSolutionType().toLowerCase());

        args.add("--model_option");
        args.add(analysisCreatedEvent.getModelHyperparameters());
        args.add("--fe_option");
        args.add(analysisCreatedEvent.getFeatureHyperparameters());
        args.add("--learning_option");
        args.add(analysisCreatedEvent.getLearningHyperparameters());


        List<EnvVar> envs = new ArrayList<>();
        envs.add(new EnvVar("job_type", DatafabricConst.ANALYSIS, null));
        envs.add(new EnvVar("analysis_id", analysisCreatedEvent.getAnalysisId().toString(), null));


        List<Volume> volumes = new ArrayList<>();
        Volume volume = new Volume();
        PersistentVolumeClaimVolumeSource pvc = new PersistentVolumeClaimVolumeSource();
        pvc.setClaimName(kubernetesConfig.getPvcName());
        volume.setName(kubernetesConfig.getPvcName());
        volume.setPersistentVolumeClaim(pvc);
        volumes.add(volume);

        VolumeMount volumeMounts = new VolumeMount();
        volumeMounts.setName(kubernetesConfig.getPvcName());
        volumeMounts.setMountPath(kubernetesConfig.getMountPath());

        String solutionType = Optional.ofNullable(analysisCreatedEvent.getSolutionType())
            .orElse("default").toLowerCase();

        Container container = containerBuilder.withName(solutionType)
            .withArgs(args).withEnv(envs).withCommand(command)
            .withVolumeMounts(volumeMounts).withImage(imageConfig.getAnalysisImageName())
            .withResources(resourceRequirements)
            .build();

        List<DagTask> tasks = new ArrayList<>();
        Template template = Template.createTemplate(DatafabricConst.ANALYSIS, container);
        templates.add(template);

        DagTask dagTask = DagTask.createDagTask(DatafabricConst.ANALYSIS, DatafabricConst.ANALYSIS);
        tasks.add(dagTask);

        String dagName = "dag";
        DagTemplate dag = DagTemplate.createDagTemplate(tasks);
        Template dagTemplate = Template.createTemplate(dagName, dag);
        templates.add(dagTemplate);

        WorkflowSpec workflowSpec = WorkflowSpec.createWorkflowSpec(dagName, volumes,
            kubernetesConfig.getArgoServiceaccountName(), templates);

        ObjectMetaBuilder objectMetaBuilder = new ObjectMetaBuilder();

        ObjectMeta objectMeta = objectMetaBuilder.withNamespace(kubernetesConfig.getNamespace())
            .withName(String.format("workflow-%s-%d", KubernetesLabelConst.ANALYSIS,
                analysisCreatedEvent.getAnalysisId()))
            .withLabels(labels).build();

        Workflow workflow = Workflow.createWorkflow(objectMeta, workflowSpec);

        workflowClient.createOrReplaceWorkflow(workflow);
    }
}
