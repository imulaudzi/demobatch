package com.batch.demoBatch.Dao;

import com.batch.demoBatch.Dao.model.BatchQueue;
import com.batch.demoBatch.Dao.model.JobParams;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by isaac on 5/28/2017.
 */
@Repository
@Transactional
public interface JobParamsRepository extends CrudRepository<JobParams,Long> {

    @Query("SELECT p FROM JobParams p WHERE p.jobId = :jobId")
    List<JobParams> findAllByJobParamsByJobId(@Param("jobId")BatchQueue jobId);
}
