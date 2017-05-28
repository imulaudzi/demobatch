package com.batch.demoBatch.scheduler;

import com.batch.demoBatch.DTO.BatchParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Created by isaac on 2017/05/24.
 */
@Component
@Transactional
public class BatchScheduler implements ApplicationListener<ContextRefreshedEvent> {

    private DefaultManagedTaskScheduler taskScheduler;

    private static Logger LOGGER = LoggerFactory.getLogger(BatchScheduler.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        LOGGER.info("Hello");

        CronTrigger cronTrigger = new CronTrigger("*/10 * * * * *", TimeZone.getTimeZone("GMT"));

        Runnable task =() -> {
            LOGGER.info("RUNNING...");
            BatchParam batchParam = new BatchParam();
            batchParam.setStartDate(LocalDateTime.now().minusDays(2));
            batchParam.setEndDate(LocalDateTime.now());
            batchParam.setStatus("PENDING");

            LOGGER.info("Starting the Batch from [{}] to [{}]",batchParam.getEndDate(),batchParam.getStartDate());


        };

        taskScheduler = new DefaultManagedTaskScheduler();
        taskScheduler.schedule(task,cronTrigger);
    }
}
