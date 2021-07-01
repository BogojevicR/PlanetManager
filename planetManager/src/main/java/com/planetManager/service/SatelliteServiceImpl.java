package com.planetManager.service;

import com.planetManager.model.Satellite;
import com.planetManager.repository.SatelliteRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SatelliteServiceImpl implements SatelliteService {

    private final SatelliteRepository satelliteRepository;

    public SatelliteServiceImpl(SatelliteRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    @Override
    public Satellite save(Satellite satellite) {
        return satelliteRepository.save(satellite);
    }

    @Override
    public Satellite update(Satellite updateSatellite, Long id) throws Exception {
        return satelliteRepository.findById(id)
                .map(satellite -> {
                    satellite.setName(updateSatellite.getName());
                    satellite.setSurfaceArea(updateSatellite.getSurfaceArea());
                    satellite.setMass(updateSatellite.getMass());
                    satellite.setNaturalSatellite(updateSatellite.getNaturalSatellite());
                    satellite.setPlanet(updateSatellite.getPlanet());

                    return satelliteRepository.save(satellite);
                })
                .orElseThrow(() -> new Exception("NOT FOUND"));
    }


    @Override
    public Satellite getSatelliteById(Long id) throws Exception {
        return satelliteRepository.findById(id)
                .orElseThrow(() -> new Exception("NOT FOUND"));
    }

    @Override
    public void delete(Long id) throws Exception {
        try {
            satelliteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new Exception("NOT FOUND");
        }
    }
}
