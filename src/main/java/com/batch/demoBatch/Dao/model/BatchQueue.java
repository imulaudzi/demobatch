package com.batch.demoBatch.Dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by isaac on 2017/05/26.
 */
@Entity
@Table(name = "BATCH_QUEUE")
public class BatchQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JOB_ID")
    private Long jobId;
    @Column(name = "JOB_STATUS")
    private String jobStatus;
    @Column(name = "RETRY_COUNT")
    private Integer retryCount;
    @Column(name = "LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
