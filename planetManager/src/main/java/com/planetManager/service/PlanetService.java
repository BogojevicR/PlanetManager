package com.planetManager.service;

import com.planetManager.model.Planet;
import com.planetManager.model.Satellite;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlanetService {

    Planet save(Planet planet);

    Planet getPlanetById(Long id);

    Page<Planet> getAllPlanetsByPlanetName(String name, int page, int size);

    Page<Planet> getAllPlanetsByNumberOfSatellites(int page, int size);

    Planet update(Planet planet, Long Id);

    void delete(Long id);

    List<Satellite> getListOfPlanetSatellites(Long id);
}
