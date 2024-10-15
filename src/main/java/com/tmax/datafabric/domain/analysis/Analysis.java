package com.tmax.datafabric.domain.analysis;

import java.time.LocalDateTime;
import javax.persistence.Column;
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
@Table(name = "datafabric_analysis")
public class Analysis {

    @Id
    @Column(name = "analysis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String inputDataPath;
    private String solutionType;

    @Embedded
    private HyperParameter hyperparameter;

    private String creator;

    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    private AnalysisStatus status;

    @Embedded
    private ResourceSpec resourceSpec;

    protected Analysis(String name, String inputDataPath, String solutionType,
        HyperParameter hyperparameter, ResourceSpec resourceSpec, String creator) {
        this.name = name;
        this.inputDataPath = inputDataPath;
        this.solutionType = solutionType;
        this.hyperparameter = hyperparameter;
        this.resourceSpec = resourceSpec;
        this.creator = creator;
        this.status = AnalysisStatus.READY;
    }

    public static Analysis createAnalysis(String name, String inputDataPath,String solutionType,
        HyperParameter hyperparameter, ResourceSpec resourceSpec, String creator) {
        return new Analysis(name, inputDataPath, solutionType, hyperparameter, resourceSpec, creator);
    }

    public void running() {
        this.status = AnalysisStatus.RUNNING;
    }

    public void fail() {
        this.status = AnalysisStatus.FAIL;
    }

    public void finished() {
        this.status = AnalysisStatus.COMPLETE;
    }
}
