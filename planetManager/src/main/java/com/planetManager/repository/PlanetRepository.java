package com.planetManager.repository;

import com.planetManager.model.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends PagingAndSortingRepository<Planet, Long> {

    Page<Planet> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT pl FROM Planet pl order by pl.satellites.size desc")
    Page<Planet> findAllBySatellitesSize(Pageable pageable);
}
