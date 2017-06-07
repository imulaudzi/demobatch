package com.batch.demoBatch.Dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by isaac on 2017/05/26.
 */
@Entity
@Table(name = "JOB_PARAMS")
public class JobParams {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PARAM_ID")
    private Long paramId;
    @Column(name = "PARAM_KEY")
    private String paramKey;
    @Column(name = "PARAM_VALUE")
    private String paramValue;
//    @Column(name = "TYPE")
//    private String type;
    @ManyToOne
    @JoinColumn(name = "JOB_ID")
    private BatchQueue jobId;

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }
    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public BatchQueue getJobId() {
        return jobId;
    }

    public void setJobId(BatchQueue jobId) {
        this.jobId = jobId;
    }
}
