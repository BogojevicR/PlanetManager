package com.planetManager.service;

import com.planetManager.controller.exception.EntityNotFoundCustomException;
import com.planetManager.model.Satellite;
import com.planetManager.repository.SatelliteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.planetManager.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SatelliteServiceImplTest {

    @Mock
    private SatelliteRepository satelliteRepository;

    @InjectMocks
    SatelliteServiceImpl satelliteService;

    @Captor
    private ArgumentCaptor<Satellite> satelliteArgumentCaptor;

    @Test
    void save() {
        Satellite satellite = createSatellite();

        satelliteService.save(satellite);

        verify(satelliteRepository).save(satelliteArgumentCaptor.capture());
        Satellite argumentSatellite = satelliteArgumentCaptor.getValue();

        assertSatellite(argumentSatellite, satellite);
    }

    @Test
    void update() {
        Satellite satellite = createSatellite();

        Satellite updatedSatellite = createSatellite();
        updatedSatellite.setName(UPDATE_STRING_VALUE);

        when(satelliteRepository.findById(any())).thenReturn(Optional.of(satellite));
        when(satelliteRepository.save(any())).thenReturn(updatedSatellite);

        satelliteService.update(updatedSatellite, SATELLITE_ID);

        verify(satelliteRepository).save(satelliteArgumentCaptor.capture());
        Satellite argumentSatellite = satelliteArgumentCaptor.getValue();

        assertSatellite(argumentSatellite, updatedSatellite);
    }

    @Test
    void getSatelliteById() {
        Satellite satellite = createSatellite();

        when(satelliteRepository.findById(any())).thenReturn(Optional.of(satellite));

        assertSatellite(satelliteService.getSatelliteById(SATELLITE_ID), satellite);
    }

    @Test
    void getSatelliteByIdThrowsException() {
        when(satelliteRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundCustomException.class, () -> satelliteService.getSatelliteById(SATELLITE_ID));
    }

    @Test
    void delete() {
        satelliteService.delete(SATELLITE_ID);

        verify(satelliteRepository, times(1)).deleteById(any());
    }
}