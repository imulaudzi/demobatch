package com.batch.demoBatch.Dao;

import com.batch.demoBatch.Dao.model.BatchQueue;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by isaac on 5/28/2017.
 */
@Repository
@Transactional
public interface BatchQueueRepository extends CrudRepository<BatchQueue,Long>{


    @Query("SELECT b FROM BatchQueue b WHERE b.jobStatus NOT IN(:jobStatus) order by b.jobId asc")
    public List<BatchQueue> findFirstByJobStatusIsNotInOrderByJobIdAsc(@Param("jobStatus") String jobStatus);

    @Query("SELECT b FROM BatchQueue b WHERE b.jobStatus IN(:jobStatus) order by b.jobId asc")
    public List<BatchQueue> findFirstByJobStatusIsOrderByJobIdJobIdAsc(@Param("jobStatus") String jobStatus);

    @Modifying
    @Query("UPDATE BatchQueue b set b.jobStatus = :newJobStatus WHERE b.jobStatus = :currentJobStatus")
    public void updateBatchJobStatus(@Param("newJobStatus")String newJobStatus,@Param("currentJobStatus") String currentJobStatus);

    @Modifying
    @Query("UPDATE BatchQueue b set b.jobStatus = :newJobStatus,b.lastUpdateTime = :lastUpdateTime WHERE b.jobId = :jobId")
    public void updateBatchJobStatusByJobId(@Param("newJobStatus")String newJobStatus, @Param("lastUpdateTime")LocalDateTime lastUpdateTime, @Param("jobId") Long jobId);

}
