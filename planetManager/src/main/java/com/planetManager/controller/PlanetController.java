package com.planetManager.controller;

import com.planetManager.model.Planet;
import com.planetManager.model.Satellite;
import com.planetManager.service.PlanetService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planet")
public class PlanetController {

    private PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    public ResponseEntity<Planet> save(@RequestBody @Valid Planet planet) {
        return new ResponseEntity<>(planetService.save(planet), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getPlanetById(@PathVariable Long id) {
        return new ResponseEntity<>(planetService.getPlanetById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<Planet>> getAllPlanetsByPlanetName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Planet> allPlanetsByPlanetName = planetService.getAllPlanetsByPlanetName(name, page, size);

        return new ResponseEntity<>(allPlanetsByPlanetName, HttpStatus.OK);
    }

    @GetMapping("/bySatelliteNumber")
    public ResponseEntity<Page<Planet>> getAllPlanetsByNumberOfSatellites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Planet> allPlanetsByPlanetName = planetService.getAllPlanetsByNumberOfSatellites(page, size);

        return new ResponseEntity<>(allPlanetsByPlanetName, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planet> update(@RequestBody @Valid Planet planet, @PathVariable("id") Long id) {
        return new ResponseEntity<>(planetService.update(planet, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Planet> delete(@PathVariable Long id) {
        planetService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/satellites")
    public ResponseEntity<List<Satellite>> getListOfPlanetSatellites(@PathVariable Long id) {
        return new ResponseEntity<>(planetService.getListOfPlanetSatellites(id), HttpStatus.OK);
    }
}
