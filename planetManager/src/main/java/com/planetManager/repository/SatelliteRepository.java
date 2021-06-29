package com.planetManager.repository;

import com.planetManager.model.Satellite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatelliteRepository extends CrudRepository<Satellite, Long> {
}
