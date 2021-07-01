package com.planetManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetManager.model.Planet;
import com.planetManager.repository.PlanetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static com.planetManager.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("application-test")
@AutoConfigureMockMvc
class PlanetControllerIntegrationTest {

    private final static String URI = "/api/planets";

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        planetRepository.deleteAll();
    }

    @Test
    void saveValidPlanet() throws Exception {
        Planet planet = createPlanet();

        MvcResult result = mockMvc.perform(
                post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(planet))
        )
                .andExpect(status().isCreated())
                .andReturn();

        Planet resultPlanet = mapper.readValue(result.getResponse().getContentAsString(), Planet.class);

        assertThat(planetRepository.count()).isEqualTo(1);
        assertPlanet(resultPlanet, planet);
    }

    @Test
    void saveInvalidPlanet() throws Exception {
        Planet planet = createPlanet();
        planet.setMass(null);

        mockMvc.perform(
                post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(planet))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.errors.length()", equalTo(1)));
    }

    @Test
    void getPlanetByIdForExistingPlanet() throws Exception {
        Planet planet = createPlanet();

        Planet savedPlanet = planetRepository.save(planet);

        MvcResult result = mockMvc.perform(
                get(URI.concat("/").concat(savedPlanet.getId().toString()))
        )
                .andExpect(status().isOk())
                .andReturn();

        Planet resultPlanet = mapper.readValue(result.getResponse().getContentAsString(), Planet.class);

        assertPlanet(resultPlanet, planet);
    }

    @Test
    void getPlanetByIdForNonExistingPlanet() throws Exception {
        mockMvc.perform(
                get(URI.concat("/").concat(PLANET_ID.toString()))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Test
    void getAllPlanetsByPlanetName() throws Exception {
        Planet planet = createPlanet();
        planet.setName("Planet");
        planet.setId(1L);
        planetRepository.save(planet);


        Planet planet1 = createPlanet();
        planet1.setName("Filter");
        planet1.setId(11L);
        planetRepository.save(planet1);

        Planet planet2 = createPlanet();
        planet2.setName("Planet2");
        planet2.setId(22L);

        planetRepository.save(planet2);


        Planet planet3 = createPlanet();
        planet3.setName("Planet3");
        planet3.setId(33L);
        planetRepository.save(planet3);

        mockMvc.perform(
                get(URI.concat("?name=pl&page=0&size=2"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", equalTo(2)))
                .andExpect(jsonPath("$.totalElements", equalTo(3)));
    }

    @Test
    void getAllPlanetsByPlanetNameWithoutNameReturnsAll() throws Exception {
        Planet planet = createPlanet();
        planet.setName("Planet");
        planet.setId(1L);
        planetRepository.save(planet);


        Planet planet1 = createPlanet();
        planet1.setName("Filter");
        planet1.setId(11L);
        planetRepository.save(planet1);

        Planet planet2 = createPlanet();
        planet2.setName("Planet2");
        planet2.setId(22L);

        planetRepository.save(planet2);


        Planet planet3 = createPlanet();
        planet3.setName("Planet3");
        planet3.setId(33L);
        planetRepository.save(planet3);

        mockMvc.perform(
                get(URI.concat("?page=0&size=2"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", equalTo(2)))
                .andExpect(jsonPath("$.totalElements", equalTo(4)));
    }

    @Test
    void getAllPlanetsByPlanetNameWithDefaultSize() throws Exception {
        Planet planet = createPlanet();
        planet.setName("Planet");
        planet.setId(1L);
        planetRepository.save(planet);


        Planet planet1 = createPlanet();
        planet1.setName("Filter");
        planet1.setId(11L);
        planetRepository.save(planet1);

        Planet planet2 = createPlanet();
        planet2.setName("Planet2");
        planet2.setId(22L);
        planetRepository.save(planet2);


        Planet planet3 = createPlanet();
        planet3.setName("Planet3");
        planet3.setId(33L);
        planetRepository.save(planet3);

        mockMvc.perform(
                get(URI.concat("?page=0"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", equalTo(1)))
                .andExpect(jsonPath("$.totalElements", equalTo(4)));
    }

    @Test
    void getAllPlanetsByNumberOfSatellites() throws Exception {
        Planet planet = createPlanet();
        planet.setName("Planet");
        planet.setId(1L);
        planetRepository.save(planet);

        Planet planet1 = createPlanet();
        planet1.setName("Planet1");
        planet1.setSatellites(new ArrayList<>());
        planet1.setId(11L);
        planetRepository.save(planet1);

        Planet planet2 = createPlanet();
        planet2.setName("Planet2");
        planet2.addSatellite(createSatellite());
        planet2.setId(22L);
        planetRepository.save(planet2);

        Planet planet3 = createPlanet();
        planet3.setName("Planet3");
        planet3.addSatellite(createSatellite());
        planet3.addSatellite(createSatellite());
        planet3.setId(33L);
        planetRepository.save(planet3);

        mockMvc.perform(
                get(URI.concat("/bySatelliteNumber?page=0&size=3"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", equalTo(2)))
                .andExpect(jsonPath("$.totalElements", equalTo(4)))
                .andExpect(jsonPath("$.content[0].satellites.length()", equalTo(planet3.getSatellites().size())))
                .andExpect(jsonPath("$.content[1].satellites.length()", equalTo(planet2.getSatellites().size())))
                .andExpect(jsonPath("$.content[2].satellites.length()", equalTo(planet.getSatellites().size())));
    }

    @Test
    void updateValidPlanet() throws Exception {
        Planet planet = planetRepository.save(createPlanet());
        Planet updatedPlanet = createPlanet();

        updatedPlanet.setName(UPDATE_STRING_VALUE);
        updatedPlanet.setSurfaceArea(UPDATE_NUMBER_VALUE);
        updatedPlanet.setMass(UPDATE_NUMBER_VALUE);
        updatedPlanet.setDistanceFromSun(UPDATE_NUMBER_VALUE);
        updatedPlanet.setAverageSurfaceTemperature(UPDATE_NUMBER_VALUE.intValue());
        updatedPlanet.setSatellites(new ArrayList<>());

        MvcResult result = mockMvc.perform(
                put(URI.concat("/").concat(planet.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedPlanet))
        )
                .andExpect(status().isOk())
                .andReturn();

        Planet resultPlanet = mapper.readValue(result.getResponse().getContentAsString(), Planet.class);

        assertPlanet(resultPlanet, updatedPlanet);
    }

    @Test
    void updateInvalidPlanet() throws Exception {
        Planet planet = planetRepository.save(createPlanet());
        planet.setName(null);
        planet.setSurfaceArea(null);

        mockMvc.perform(
                put(URI.concat("/").concat(planet.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(planet))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.errors.length()", equalTo(2)));
    }

    @Test
    void updateInvalidNonExistingPlanet() throws Exception {
        mockMvc.perform(
                put(URI.concat("/").concat(PLANET_ID.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createPlanet()))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Test
    void deleteExistingPlanet() throws Exception {
        Planet planet = planetRepository.save(createPlanet());
        mockMvc.perform(
                delete(URI.concat("/").concat(planet.getId().toString()))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNonExistingPlanet() throws Exception {
        mockMvc.perform(
                delete(URI.concat("/").concat(PLANET_ID.toString()))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Test
    void getListOfPlanetSatellites() throws Exception {
        Planet planet = createPlanet();
        planet.addSatellite(createSatellite());

        Planet savedPlanet = planetRepository.save(planet);

        mockMvc.perform(
                get(URI.concat("/").concat(savedPlanet.getId().toString()).concat("/satellites"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(2)));
    }

    @Test
    void getListOfPlanetSatellitesForNonExistingPlanet() throws Exception {
        mockMvc.perform(
                get(URI.concat("/").concat(PLANET_ID.toString()).concat("/satellites"))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }
}