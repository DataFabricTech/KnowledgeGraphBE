package com.tmax.datafabric.kubernetesclient.pod;

import java.io.InputStream;

public interface FileTransferWithPod {
    InputStream downloadFileFromPod(String srcPath, String namespace, String podName);
}
