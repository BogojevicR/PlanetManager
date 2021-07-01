package com.planetManager.service;

import com.planetManager.model.Satellite;
import org.springframework.stereotype.Service;

@Service
public interface SatelliteService {

    Satellite save(Satellite satellite);

    Satellite update(Satellite updateSatellite, Long id);

    Satellite getSatelliteById(Long id);

    void delete(Long id);

}
