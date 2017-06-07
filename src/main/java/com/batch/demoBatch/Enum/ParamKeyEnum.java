package com.batch.demoBatch.Enum;

import java.util.List;

/**
 * Created by isaac on 2017/05/26.
 */
public enum ParamKeyEnum {

    STARTDATE("startDate"),
    ENDDATE("endDate"),
    TYPE("type"),
    STATUS("status");

    private String key;
    private String type;

    ParamKeyEnum(String key) {
        this.key = key;

    }

    public String getKey() {
        return key;
    }
}
