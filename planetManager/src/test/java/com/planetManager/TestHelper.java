package com.planetManager;

import com.planetManager.model.Planet;
import com.planetManager.model.Satellite;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelper {

    public static final Long PLANET_ID = 1L;
    public static final String PLANET_NAME = "Test Planet";
    public static final String UPDATE_STRING_VALUE = "Update";
    public static final Long UPDATE_NUMBER_VALUE = 999L;
    public static final Long SURFACE_AREA = 100L;
    public static final Long MASS = 200L;
    public static final Long DISTANCE_FROM_SUN = 300L;
    public static final Integer AVERAGE_SURFACE_TEMPERATURE = 25;

    public static final Long SATELLITE_ID = 10L;
    public static final String SATELLITE_NAME = "Test Satellite";


    public static Planet createPlanet() {
        Planet planet = new Planet();
        planet.setId(PLANET_ID);
        planet.setName(PLANET_NAME);
        planet.setSurfaceArea(SURFACE_AREA);
        planet.setMass(MASS);
        planet.setDistanceFromSun(DISTANCE_FROM_SUN);
        planet.setAverageSurfaceTemperature(AVERAGE_SURFACE_TEMPERATURE);

        planet.addSatellite(createSatellite());

        return planet;
    }

    public static Satellite createSatellite() {
        Satellite satellite = new Satellite();
        satellite.setId(SATELLITE_ID);
        satellite.setName(SATELLITE_NAME);
        satellite.setSurfaceArea(SURFACE_AREA);
        satellite.setMass(MASS);
        satellite.setNaturalSatellite(false);

        return satellite;
    }

    public static void assertPlanet(Planet result, Planet input) {
        assertEquals(result.getSurfaceArea(), input.getSurfaceArea());
        assertEquals(result.getMass(), input.getMass());
        assertEquals(result.getDistanceFromSun(), input.getDistanceFromSun());
        assertEquals(result.getAverageSurfaceTemperature(), input.getAverageSurfaceTemperature());
        assertEquals(result.getSatellites().size(), input.getSatellites().size());
    }

    public static void assertSatellite(Satellite result, Satellite input) {
        assertEquals(result.getId(), input.getId());
        assertEquals(result.getSurfaceArea(), input.getSurfaceArea());
        assertEquals(result.getMass(), input.getMass());
        assertEquals(result.getNaturalSatellite(), input.getNaturalSatellite());
        assertEquals(result.getPlanet(), input.getPlanet());
    }
}
