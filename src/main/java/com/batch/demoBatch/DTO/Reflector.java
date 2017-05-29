package com.batch.demoBatch.DTO;

import com.batch.demoBatch.Dao.model.JobParams;
import com.batch.demoBatch.Enum.ParamKeyEnum;

import java.lang.reflect.Field;

/**
 * Created by isaac on 5/28/2017.
 */
public class Reflector {

    Class aClass = BatchParam.class;
//    Field field = aClass.get;

    public Reflector() throws NoSuchFieldException {
    }
}
