package com.planetManager.service;

import com.planetManager.model.Planet;
import com.planetManager.model.Satellite;
import com.planetManager.repository.PlanetRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetServiceImpl(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Override
    public Planet save(Planet planet) {
        return planetRepository.save(planet);
    }

    @Override
    public Planet getPlanetById(Long id) throws Exception {
        return planetRepository.findById(id)
                .orElseThrow(() -> new Exception("NOT FOUND"));
    }

    @Override
    public Page<Planet> getAllPlanetsByPlanetName(String name, int page, int size) {
        if (name == null) {
            return planetRepository.findAll(PageRequest.of(page, size, Sort.by("name").ascending()));
        }
        return planetRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, size, Sort.by("name").ascending()));
    }

    @Override
    public Page<Planet> getAllPlanetsByNumberOfSatellites(int page, int size) {
        return planetRepository.findAllBySatellitesSize(PageRequest.of(page, size));
    }

    @Override
    public Planet update(Planet updatedPlanet, Long id) throws Exception {
        return planetRepository.findById(id)
                .map(planet -> {
                    planet.setName(updatedPlanet.getName());
                    planet.setSurfaceArea(updatedPlanet.getSurfaceArea());
                    planet.setMass(updatedPlanet.getMass());
                    planet.setDistanceFromSun(updatedPlanet.getDistanceFromSun());
                    planet.setAverageSurfaceTemperature(updatedPlanet.getAverageSurfaceTemperature());
                    planet.setSatellites(updatedPlanet.getSatellites());

                    return planetRepository.save(planet);
                })
                .orElseThrow(() -> new Exception("NOT FOUND"));
    }

    @Override
    public void delete(Long id) throws Exception {
        try {
            planetRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new Exception("NOT FOUND");
        }
    }

    @Override
    public List<Satellite> getListOfPlanetSatellites(Long id) throws Exception {
        Optional<Planet> planet = planetRepository.findById(id);
        if (planet.isPresent()) {
            return planet.get().getSatellites();
        } else {
            throw new Exception("NOT FOUND");
        }
    }
}
