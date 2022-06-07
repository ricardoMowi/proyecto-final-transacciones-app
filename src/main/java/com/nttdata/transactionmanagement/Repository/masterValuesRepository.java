package com.nttdata.transactionmanagement.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.transactionmanagement.redis.model.MasterValuesCache;

@Repository
public interface masterValuesRepository  extends MongoRepository <MasterValuesCache, String> {
    
}
