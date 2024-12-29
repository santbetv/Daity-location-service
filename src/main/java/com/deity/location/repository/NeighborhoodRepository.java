package com.deity.location.repository;

import com.deity.location.domain.mongo.Neighborhood;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

//No se utilizar ya que utlizamos  ReactiveMongoTemplate
@Repository
public interface NeighborhoodRepository extends ReactiveMongoRepository<Neighborhood, String> {
}