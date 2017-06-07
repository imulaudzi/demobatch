package com.batch.demoBatch.service;

import com.batch.demoBatch.Dao.BatchQueueRepository;
import com.batch.demoBatch.Dao.model.BatchQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by isaac on 2017/05/29.
 */
@Component
@Transactional
public class BatchServiceActivator implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BatchQueueRepository batchQueueRepository;

    private DefaultManagedTaskScheduler taskScheduler;
    private static Logger LOGGER = LoggerFactory.getLogger(BatchServiceActivator.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        batchQueueRepository.updateBatchJobStatus("PENDING", "PROCESSING");

        LOGGER.info("************......RESET........********");

        Runnable task = () -> {
            List<BatchQueue> batchQueue = batchQueueRepository.findFirstByJobStatusIsNotInOrderByJobIdAsc("FAILED");
            BatchQueue queue;
            if (batchQueue != null && !batchQueue.isEmpty()) {
                LOGGER.info("**********..........We have messages");
                queue = batchQueue.get(0);
                if ("PROCESSING".equalsIgnoreCase(queue.getJobStatus())) {
                    LOGGER.info("************.......ANOTHER JOB IS STILL RUNNING.. WILL WAIT!!");
                    LOGGER.info("************.......*** Has been running since: ,[{}]", queue.getLastUpdateTime());
                } else {
                    LOGGER.info("************.......NO JOB IS RUNNING.. WILL START 1 NOW!!");

                    batchQueueRepository.updateBatchJobStatusByJobId("PROCESSING", LocalDateTime.now(), queue.getJobId());
                    LOGGER.info("************.......JOB STARTED");
                }
            } else {
                LOGGER.info("************.......NO NEW JOB(S) TO RUN!!!");

                List<BatchQueue> retryFailedBatch = batchQueueRepository.findFirstByJobStatusIsOrderByJobIdJobIdAsc("FAILED");

                if(retryFailedBatch == null || !retryFailedBatch.isEmpty()){
                    queue = retryFailedBatch.get(0);

                }

            }
        };
        CronTrigger cronTrigger = new CronTrigger("*/10 * * * * *", TimeZone.getTimeZone("GMT"));
        taskScheduler = new DefaultManagedTaskScheduler();
        taskScheduler.schedule(task, cronTrigger);


    }
}
