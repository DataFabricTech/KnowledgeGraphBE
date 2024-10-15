package com.tmax.datafabric.domain.train;

import java.time.LocalDateTime;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "datafabric_train")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analysisId;

    private String name;
    private String inputDataPath;
    private String solutionType;
    private String modelType;

    @Embedded
    private HyperParameter hyperparameter;

    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    private String status;

    protected Analysis(String name, String inputDataPath, String solutionType, String modelType,
        HyperParameter hyperparameter) {
        this.name = name;
        this.inputDataPath = inputDataPath;
        this.solutionType = solutionType;
        this.modelType = modelType;
        this.hyperparameter = hyperparameter;
        this.status = "READY";
    }

    public static Analysis createTrain(String name, String inputDataPath,String solutionType,
        String modelType, HyperParameter hyperparameter) {
        return new Analysis(name, solutionType, inputDataPath, modelType, hyperparameter);
    }

    public void running() {
        this.status = "RUNNING";
    }

    public void fail() {
        this.status = "FAILED";
    }
}
