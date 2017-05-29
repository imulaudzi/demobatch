package com.batch.demoBatch.Enum;

import java.util.List;

/**
 * Created by isaac on 2017/05/26.
 */
public enum ParamKeyEnum {

    STARTDATE("StartDate", "LocalDateTime"),
    ENDDATE("EndDate", "LocalDateTime");

    private String key;
    private String type;

    ParamKeyEnum(String key, String type) {
        this.key = key;
        this.type = type;

    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }
}
