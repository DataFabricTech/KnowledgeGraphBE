package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.application.config.DatafabricConst;
import com.tmax.datafabric.application.config.ImageConfig;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.DagTask;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.DagTemplate;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Template;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Workflow;
import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.WorkflowSpec;
import com.tmax.datafabric.domain.event.Event;
import com.tmax.datafabric.domain.event.TrainCreatedEvent;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.tmax.datafabric.domain.outbox.OutboxEventHandlerHelper;
import com.tmax.datafabric.domain.train.AnalysisRepository;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimVolumeSource;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeMount;
import io.fabric8.kubernetes.client.KubernetesClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainCreatedOutboxEventHandlerHelper implements OutboxEventHandlerHelper {

    private final AnalysisRepository analysisRepository;
    private final ImageConfig imageConfig;
    private final KubernetesClient kubernetesClient;
    private final KubernetesConfig kubernetesConfig;

    @Override
    public boolean support(OutboxEvent outboxEvent) {
        return outboxEvent.getAggregateType().equals(AggregateType.DATAFABRIC_TRAIN)
            && outboxEvent.getEventType().equals(TrainCreatedEvent.class.getName());
    }

    @Override
    public void handle(Event domainEvent) {

        TrainCreatedEvent trainCreatedEvent = (TrainCreatedEvent) domainEvent;
        if (!analysisRepository.findById(trainCreatedEvent.getTrainId()).isPresent()) {
            return;
        }

        createWorkflowFromTrain(trainCreatedEvent);

    }

    private void createWorkflowFromTrain(TrainCreatedEvent trainCreatedEvent){
        List<String> command = Arrays.asList("sh", "-c");

        ObjectMetaBuilder objectMetaBuilder = new ObjectMetaBuilder();
        ContainerBuilder containerBuilder = new ContainerBuilder();

        Map<String, String> labels = Map.of(DatafabricConst.DATAFABRIC, DatafabricConst.TRAIN,
            DatafabricConst.TRAIN_ID, trainCreatedEvent.getTrainId().toString());

        List<Template> templates = new ArrayList<Template>();

        ResourceRequirements resourceRequirements = new ResourceRequirements();
        Map<String, Quantity> resource = new HashMap<>();
        resource.put("cpu", new Quantity("1"));
        resource.put("memory", new Quantity("1"));
        resourceRequirements.setLimits(resource);
        resourceRequirements.setRequests(resource);


        List<String> args = new ArrayList<>();
        args.add("--model_option");
        args.add(trainCreatedEvent.getModelHyperparameters());
        args.add("--fe_option");
        args.add(trainCreatedEvent.getFeatureHyperparameters());
        args.add("--learning_option");
        args.add(trainCreatedEvent.getLearningHyperparameters());


        List<EnvVar> envs = new ArrayList<>();
        envs.add(new EnvVar("job_type", DatafabricConst.TRAIN, null));
        envs.add(new EnvVar("train_id", trainCreatedEvent.getTrainId().toString(), null));


        List<Volume> volumes = new ArrayList<>();
        Volume volume = new Volume();
        PersistentVolumeClaimVolumeSource pvc = new PersistentVolumeClaimVolumeSource();
        pvc.setClaimName("datafabric");
        volume.setName("datafabric");
        volume.setPersistentVolumeClaim(pvc);
        volumes.add(volume);

        VolumeMount volumeMounts = new VolumeMount();
        volumeMounts.setName("datafabric");
        volumeMounts.setMountPath(kubernetesConfig.getMountPath());


        List<DagTask> tasks = new ArrayList<>();
        Template template = Template.createTemplate(DatafabricConst.TRAIN,containerBuilder.withName(DatafabricConst.TRAIN).withArgs(args).withEnv(envs).withCommand(command)
            .withVolumeMounts(volumeMounts).withImage(imageConfig.getTrainImageName()).withResources(resourceRequirements)
            .build());
        templates.add(template);

        DagTask dagTask = DagTask.createDagTask(DatafabricConst.TRAIN, DatafabricConst.TRAIN);
        tasks.add(dagTask);

        String dagName = "dag";
        DagTemplate dag = DagTemplate.createDagTemplate(tasks);
        Template dagTemplate = Template.createTemplate(dagName, dag);
        templates.add(dagTemplate);

        WorkflowSpec workflowSpec = WorkflowSpec.createWorkflowSpec(dagName, volumes,
            kubernetesConfig.getArgoServiceaccountName(), templates);

        Workflow workflow = Workflow.createWorkflow(
            objectMetaBuilder.withNamespace(kubernetesConfig.getNamespace())
                .withName("train-"+trainCreatedEvent.getTrainId()+ RandomStringUtils.randomAlphabetic(3).toLowerCase())
                .withLabels(labels).build(), workflowSpec);

        kubernetesClient.resource(workflow).create();
    }
}
