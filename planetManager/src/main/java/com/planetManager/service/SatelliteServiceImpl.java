package com.planetManager.service;

import com.planetManager.controller.exception.EntityNotFoundCustomException;
import com.planetManager.model.Satellite;
import com.planetManager.repository.SatelliteRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
    public Satellite update(Satellite updateSatellite, Long id) {
        return satelliteRepository.findById(id)
                .map(satellite -> {
                    satellite.setName(updateSatellite.getName());
                    satellite.setSurfaceArea(updateSatellite.getSurfaceArea());
                    satellite.setMass(updateSatellite.getMass());
                    satellite.setNaturalSatellite(updateSatellite.getNaturalSatellite());
                    satellite.setPlanet(updateSatellite.getPlanet());

                    return satelliteRepository.save(satellite);
                })
                .orElseThrow(() -> new EntityNotFoundCustomException(id, Satellite.class.getSimpleName()));
    }


    @Override
    public Satellite getSatelliteById(Long id) {
        return satelliteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundCustomException(id, Satellite.class.getSimpleName()));
    }

    @Override
    public void delete(Long id) {
        try {
            satelliteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundCustomException(id, Satellite.class.getSimpleName());
        }
    }
}
