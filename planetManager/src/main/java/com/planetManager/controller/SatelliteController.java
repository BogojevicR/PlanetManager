package com.planetManager.controller;

import com.planetManager.model.Satellite;
import com.planetManager.service.SatelliteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/satellites")
public class SatelliteController {

    private final SatelliteService satelliteService;

    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    @PostMapping
    public ResponseEntity<Satellite> save(@RequestBody @Valid Satellite satellite) {
        return new ResponseEntity<>(satelliteService.save(satellite), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Satellite> update(@RequestBody @Valid Satellite satellite, @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(satelliteService.update(satellite, id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Satellite> getSatelliteById(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(satelliteService.getSatelliteById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Satellite> deleteSatelliteById(@PathVariable("id") Long id) throws Exception {
        satelliteService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
