package com.planetManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetManager.model.Satellite;
import com.planetManager.service.SatelliteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.planetManager.TestHelper.createSatellite;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SatelliteControllerTest {

    private final String URI = "/api/satellites";

    @Mock
    private SatelliteServiceImpl satelliteService;

    @InjectMocks
    SatelliteController satelliteController;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(satelliteController).build();
    }

    @Test
    void saveValidSatellite() throws Exception {
        Satellite satellite = createSatellite();

        when(satelliteService.save(any())).thenReturn(satellite);

        mockMvc.perform(
                post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(satellite))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(satellite.getName())))
                .andExpect(jsonPath("$.surfaceArea", equalTo(satellite.getSurfaceArea().intValue())))
                .andExpect(jsonPath("$.mass", equalTo(satellite.getMass().intValue())))
                .andExpect(jsonPath("$.naturalSatellite", equalTo(satellite.getNaturalSatellite())));
    }

    @Test
    void saveInvalidSatellite() throws Exception {
        Satellite satellite = createSatellite();
        satellite.setMass(null);

        mockMvc.perform(
                post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(satellite))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateValidSatellite() throws Exception {
        Satellite satellite = createSatellite();

        when(satelliteService.update(any(), any())).thenReturn(satellite);

        mockMvc.perform(
                put(URI.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(satellite))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(satellite.getName())))
                .andExpect(jsonPath("$.surfaceArea", equalTo(satellite.getSurfaceArea().intValue())))
                .andExpect(jsonPath("$.mass", equalTo(satellite.getMass().intValue())))
                .andExpect(jsonPath("$.naturalSatellite", equalTo(satellite.getNaturalSatellite())));
    }

    @Test
    void updateInvalidSatellite() throws Exception {
        Satellite satellite = createSatellite();
        satellite.setMass(null);

        mockMvc.perform(
                put(URI.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(satellite))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void getSatelliteById() throws Exception {
        Satellite satellite = createSatellite();

        when(satelliteService.getSatelliteById(any())).thenReturn(satellite);

        mockMvc.perform(
                get(URI.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(satellite.getName())))
                .andExpect(jsonPath("$.surfaceArea", equalTo(satellite.getSurfaceArea().intValue())))
                .andExpect(jsonPath("$.mass", equalTo(satellite.getMass().intValue())))
                .andExpect(jsonPath("$.naturalSatellite", equalTo(satellite.getNaturalSatellite())));
    }

    @Test
    void deleteSatelliteById() throws Exception {
        mockMvc.perform(
                delete(URI.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }
}