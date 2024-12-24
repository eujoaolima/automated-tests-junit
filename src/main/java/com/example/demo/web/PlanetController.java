package com.example.demo.web;

import com.example.demo.entity.Planet;
import com.example.demo.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    @Autowired private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        Planet createdPlanet = planetService.create(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlanet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getById(@RequestParam("id") Long id) {
        return planetService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> getByName(@RequestParam("name") String name) {
        return planetService.getByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Planet>> list(@RequestParam(required = false) String climate, @RequestParam(required = false) String terrain) {
        List<Planet> lstPlanets = planetService.listPlanets(climate, terrain);
        return ResponseEntity.ok(lstPlanets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        planetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
