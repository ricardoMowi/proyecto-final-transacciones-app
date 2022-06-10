package com.nttdata.transactionmanagement.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.transactionmanagement.Model.MasterValues;

@Repository
public interface mvRepository extends MongoRepository <MasterValues, String>  {
    
}

