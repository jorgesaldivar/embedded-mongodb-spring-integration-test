package com.jorgesaldivar.mongodbit.repository;

import com.jorgesaldivar.mongodbit.model.SampleCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleCollectionRepository extends CrudRepository<SampleCollection, String> {

    SampleCollection findByKey(String key);

}
