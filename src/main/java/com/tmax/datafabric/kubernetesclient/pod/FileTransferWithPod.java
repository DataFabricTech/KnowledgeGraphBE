package com.tmax.datafabric.kubernetesclient.pod;

import java.io.InputStream;
import org.springframework.core.io.Resource;

public interface FileTransferWithPod {
    InputStream downloadFileFromPod(String srcPath, String namespace, String podName);
}
