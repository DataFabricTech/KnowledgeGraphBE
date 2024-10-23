package com.tmax.datafabric.application.config;

import java.util.Map;

public class KubernetesLabelConst {
    public static final String MODULE_KEY = "module";
    public static final String WORKFLOW_TYPE_KEY = "workflow-type";

    public static final String DATAFABRIC = "datafabric";

    public static final String ANALYSIS = "analysis";
    public static final String ANALYSIS_ID_KEY = "analysis-id";

    public static final Map<String, String> DEFAULT_ANALYSIS_LABELS = Map.of(MODULE_KEY, DATAFABRIC,
        WORKFLOW_TYPE_KEY, ANALYSIS);
}
