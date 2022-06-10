package com.nttdata.transactionmanagement.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nttdata.transactionmanagement.Model.MasterValues;
import com.nttdata.transactionmanagement.Repository.masterValuesRepository;
import com.nttdata.transactionmanagement.Repository.mvRepository;
import com.nttdata.transactionmanagement.redis.model.MasterValuesCache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class masterValuesService {
    
    private final masterValuesRepository repository;
    private final mvRepository repo; 

 
    public List<MasterValuesCache> getAll() {
        try {
          List<MasterValuesCache> assuranceCacheList = repository.findAll();      
          return assuranceCacheList;
        } catch (Exception e) {
          return Collections.EMPTY_LIST;
        }
    }
    
    public String storageMasterValueList(List<MasterValuesCache> masterValuesList) {
        try {
            log.info("master tttttttttttttttttttttttt" + masterValuesList);
        	Iterable<MasterValuesCache> iterable = masterValuesList;
            log.info("master tttttttttttttttttttttttt......" + iterable);
            repository.saveAll(iterable);
            return "Master values list create successfully";
        } catch (Exception e) {
            return "Error saving master values cache list. " + e.getMessage();
        }
    }

    @Cacheable(value = "itemCache")
    public List<MasterValues> getDataCache() {
        List<MasterValues> assuranceCacheList = repo.findAll();      
        return assuranceCacheList;
    }

}
