package com.planetManager.service;

import com.planetManager.TestHelper;
import com.planetManager.controller.exception.EntityNotFoundCustomException;
import com.planetManager.model.Planet;
import com.planetManager.model.Satellite;
import com.planetManager.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.planetManager.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanetServiceImplTest {

    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    PlanetServiceImpl planetService;

    @Captor
    private ArgumentCaptor<Planet> planetArgumentCaptor;

    @Test
    void save() {
        Planet planet = createPlanet();

        planetService.save(planet);

        verify(planetRepository).save(planetArgumentCaptor.capture());

        assertPlanet(planetArgumentCaptor.getValue(), planet);
    }

    @Test
    void getPlanetById() {
        Planet planet = TestHelper.createPlanet();

        when(planetRepository.findById(any())).thenReturn(Optional.of(planet));

        assertPlanet(planetService.getPlanetById(PLANET_ID), planet);
    }

    @Test()
    void getPlanetByIdThrowsException() {
        when(planetRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundCustomException.class, () -> planetService.getPlanetById(PLANET_ID));
    }

    @Test
    void update() {
        Planet planet = createPlanet();

        Planet updatedPlanet = createPlanet();
        updatedPlanet.setSatellites(new ArrayList<>());
        updatedPlanet.setName(UPDATE_STRING_VALUE);

        when(planetRepository.findById(any())).thenReturn(Optional.of(planet));
        when(planetRepository.save(any())).thenReturn(updatedPlanet);

        planetService.update(updatedPlanet, PLANET_ID);

        verify(planetRepository).save(planetArgumentCaptor.capture());

        assertPlanet(planetArgumentCaptor.getValue(), updatedPlanet);
    }

    @Test
    void delete() {
        planetService.delete(PLANET_ID);

        verify(planetRepository, times(1)).deleteById(any());
    }

    @Test
    void getListOfPlanetSatellites() {
        Planet planet = TestHelper.createPlanet();
        Satellite satellite = createSatellite();
        satellite.setId(11L);
        planet.addSatellite(satellite);

        when(planetRepository.findById(any())).thenReturn(Optional.of(planet));

        assertEquals(planetService.getListOfPlanetSatellites(PLANET_ID).size(), 2);
    }

    @Test
    void getListOfPlanetSatellitesThrowsException() {
        when(planetRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundCustomException.class, () -> planetService.getListOfPlanetSatellites(any()));
    }
}