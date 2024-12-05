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
    private String datasourceType;
    private String inputDataPath;
    private String solutionType;
    private String creator;

    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    private AnalysisStatus status;

    @Embedded
    private ResourceSpec resourceSpec;

    protected Analysis(String name, String datasourceType, String inputDataPath,
                       String solutionType, ResourceSpec resourceSpec, String creator) {
        this.name = name;
        this.datasourceType = datasourceType;
        this.inputDataPath = inputDataPath;
        this.solutionType = solutionType;
        this.resourceSpec = resourceSpec;
        this.creator = creator;
        this.status = AnalysisStatus.READY;
    }

    public static Analysis createAnalysis(String name, String datasourceType, String inputDataPath, String solutionType,
                                          ResourceSpec resourceSpec, String creator) {
        return new Analysis(name, datasourceType, inputDataPath, solutionType, resourceSpec, creator);
    }

    public void running() {
        if (this.status.equals(AnalysisStatus.READY)) {
            return;
        }

        this.status = AnalysisStatus.RUNNING;
    }

    public void fail() {
        if (this.status.equals(AnalysisStatus.COMPLETE)) {
            return;
        }

        this.status = AnalysisStatus.FAIL;
        this.finishedAt = LocalDateTime.now();
    }

    public void complete() {
        if (this.status.equals(AnalysisStatus.FAIL)) {
            return;
        }

        this.status = AnalysisStatus.COMPLETE;
        this.finishedAt = LocalDateTime.now();
    }
}
