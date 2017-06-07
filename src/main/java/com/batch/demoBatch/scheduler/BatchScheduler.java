package com.batch.demoBatch.scheduler;

import com.batch.demoBatch.DTO.BatchParam;
import com.batch.demoBatch.Dao.BatchQueueRepository;
import com.batch.demoBatch.Dao.JobParamsRepository;
import com.batch.demoBatch.Dao.model.BatchQueue;
import com.batch.demoBatch.Dao.model.JobParams;
import com.batch.demoBatch.Enum.ParamKeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by isaac on 2017/05/24.
 */
@Component
@Transactional
public class BatchScheduler implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BatchQueueRepository batchQueueRepository;
    @Autowired
    private JobParamsRepository jobParamsRepository;

    private DefaultManagedTaskScheduler taskScheduler;

    private static Logger LOGGER = LoggerFactory.getLogger(BatchScheduler.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        BatchParam param = new BatchParam();
        param.setStatus("PENDING");
        param.setType("ANyType");
        executeM(param);
    }

    private void executeM(final BatchParam batchParam){

        Runnable task = () -> {
            LOGGER.info("********** Scheduling a Job **********");

            BatchQueue batchQueue = new BatchQueue();
            batchQueue.setJobStatus("PENDING");
            batchQueue.setRetryCount(0);
            batchQueueRepository.save(batchQueue);

            try {
                reflector(batchParam, batchQueue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            LOGGER.info("********** BATCH JOB SCHEDULED **********");
        };
        CronTrigger cronTrigger = new CronTrigger("*/30 * * * * *", TimeZone.getTimeZone("GMT"));
        taskScheduler = new DefaultManagedTaskScheduler();
        taskScheduler.schedule(task, cronTrigger);
    }


    private void reflector(BatchParam batchParam, BatchQueue batchQueue) throws IllegalAccessException, NoSuchFieldException {
        Class aClass = batchParam.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            for (ParamKeyEnum keyEnum : ParamKeyEnum.values()) {
                if (field.getName().equalsIgnoreCase(keyEnum.getKey())) {
                    field = BatchParam.class.getDeclaredField(keyEnum.getKey());
                    field.setAccessible(true);
                    LOGGER.debug("RUNNING...[{}]", field.getName());
                    JobParams jobParams = new JobParams();
                    jobParams.setJobId(batchQueue);
                    jobParams.setParamKey(field.getName());

                    if (field.getName().contains("Date")) {
                        setDate(jobParams, field);
                    } else {
                        String jobParam = (String) field.get(batchParam);
                        jobParams.setParamValue(jobParam);
                    }
                    jobParamsRepository.save(jobParams);
                }
            }
        }
    }

    private void setDate(JobParams date, Field field) {
        if (field.getName().equalsIgnoreCase("StartDate")) {
            LocalDateTime startDate = LocalDateTime.now().minusDays(2);
            date.setParamValue(startDate.toString());
            LOGGER.info("********** START_DATE: [{}] **********", startDate);
        } else if (field.getName().equalsIgnoreCase("EndDate")) {
            LocalDateTime endDate = LocalDateTime.now();
            date.setParamValue(endDate.toString());
            LOGGER.info("********** END_DATE: [{}] **********", endDate);
        }
    }
}
