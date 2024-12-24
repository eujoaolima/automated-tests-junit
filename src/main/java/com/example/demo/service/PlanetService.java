package com.example.demo.service;

import com.example.demo.entity.Planet;
import com.example.demo.repository.PlanetRepository;
import com.example.demo.entity.QueryBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {
    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    public Optional<Planet> getById(Long id) {
        return planetRepository.findById(id);
    }

    public Optional<Planet> getByName(String name) {
        return planetRepository.findByName(name);
    }

    public List<Planet> listPlanets(String climate, String terrain) {
        Planet planet = new Planet(climate, terrain);
        Example<Planet> example = QueryBuilder.makeQuery(planet);
        return planetRepository.findAll(example);
    }

    public void delete(Long id) {
        planetRepository.deleteById(id);
    }
}
