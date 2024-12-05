package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.application.config.DatafabricConst;
import com.tmax.datafabric.application.config.DatasourceConfig;
import com.tmax.datafabric.application.config.ImageConfig;
import com.tmax.datafabric.application.config.KubernetesLabelConst;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.workflow.KubernetesWorkflowClient;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisCreatedOutboxEventHandlerHelper implements
    com.tmax.datafabric.application.outbox.OutboxEventHandlerHelper {

    private final AnalysisRepository analysisRepository;
    private final ImageConfig imageConfig;
    private final DatasourceConfig datasourceConfig;
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

    private void createWorkflowFromAnalysisCreatedEvent(AnalysisCreatedEvent analysisCreatedEvent) {
        List<String> command = Arrays.asList("python3", "agent/main.py");
        ContainerBuilder containerBuilder = new ContainerBuilder();

        Map<String, String> labels = new HashMap<>(KubernetesLabelConst.DEFAULT_ANALYSIS_LABELS);
        labels.put(KubernetesLabelConst.ANALYSIS_ID_KEY, String.valueOf(analysisCreatedEvent.getAnalysisId()));

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

        String datasourceType = analysisCreatedEvent.getDataSourceType();

        args.add("--datasource_type");
        args.add(datasourceType);

        if (datasourceType.equals("MinIO")) {
            String connectionConfigPath
                    = String.format("%s/%s-config.json", datasourceConfig.getPath(), datasourceType.toLowerCase());
            String filePath = getClass().getClassLoader().getResource(connectionConfigPath).getPath();
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    InputStream inputStream = new FileInputStream(filePath);
                    String jsonString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    JSONObject jsonObject = new JSONObject(jsonString);

                    args.add("--host");
                    args.add(jsonObject.get("host").toString());
                    args.add("--port");
                    args.add(jsonObject.get("port").toString());
                    args.add("--region");
                    args.add(jsonObject.get("region").toString());
                    args.add("--bucket_name");
                    args.add(jsonObject.get("bucketName").toString());
                    args.add("--access_key");
                    args.add(jsonObject.get("accessKey").toString());
                    args.add("--secret_key");
                    args.add(jsonObject.get("secretKey").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        args.add("--mode");

        if (analysisCreatedEvent.getInputDataPath().isEmpty()) {
            args.add("mock");
        } else {
            args.add("production");

            args.add("--file_source_path");
            args.add(analysisCreatedEvent.getInputDataPath());
        }

        args.add("--algorithm");
        args.add(analysisCreatedEvent.getSolutionType().toLowerCase());

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

        Container container = containerBuilder.withName(DatafabricConst.ANALYSIS)
            .withArgs(args).withEnv(envs).withCommand(command)
            .withVolumeMounts(volumeMounts).withImage(imageConfig.getAnalysisImageName())
            .withResources(resourceRequirements)
            .build();

        List<Template> templates = new ArrayList<>();

        String dagName = DatafabricConst.ANALYSIS;
        Template dagTemplate = Template.createTemplate(DatafabricConst.ANALYSIS, container);
        templates.add(dagTemplate);

        WorkflowSpec workflowSpec = WorkflowSpec.createWorkflowSpec(dagName, volumes,
            kubernetesConfig.getArgoServiceaccountName(), templates);

        ObjectMetaBuilder objectMetaBuilder = new ObjectMetaBuilder();

        ObjectMeta objectMeta = objectMetaBuilder.withNamespace(kubernetesConfig.getNamespace())
            .withName(String.format("workflow-%s-%d", KubernetesLabelConst.ANALYSIS,
                analysisCreatedEvent.getAnalysisId()))
            .withLabels(labels).build();

        Workflow workflow = Workflow.createWorkflow(objectMeta, workflowSpec);

        workflowClient.createWorkflow(workflow);
    }
}
