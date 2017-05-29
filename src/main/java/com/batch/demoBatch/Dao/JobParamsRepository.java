package com.batch.demoBatch.Dao;

import com.batch.demoBatch.Dao.model.JobParams;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by isaac on 5/28/2017.
 */
@Repository
@Transactional
public interface JobParamsRepository extends CrudRepository<JobParams,Long> {
}
