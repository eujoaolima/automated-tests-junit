package com.example.demo.service;

import com.example.demo.common.PlanetFactory;
import com.example.demo.entity.Planet;
import com.example.demo.entity.QueryBuilder;
import com.example.demo.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.common.PlanetFactory.PLANET;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    @InjectMocks private PlanetService planetService;
    @Mock private PlanetRepository planetRepository;

    @Test
    public void createPlanet_WithValidData_ReturnsCreatedPlanet() {
        Planet planet = PlanetFactory.createPlanet();

        when(planetRepository.save(planet)).thenReturn(planet);

        // System Under Test
        Planet sut = planetService.create(planet);

        assertEquals(sut, planet);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        Planet planet = PlanetFactory.createInvalidPlanet();
        when(planetRepository.save(planet)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> planetService.create(planet));
    }

    @Test
    public void getPlanet_WithUnexistingId_ReturnsEmpty() {
        when(planetRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.getById(1L);
        assertTrue(sut.isEmpty());
    }

    @Test
    public void getPlanet_WithExistingId_ReturnsPlanet() {
        Planet planet = PlanetFactory.createPlanet();
        when(planetRepository.findById(planet.getId())).thenReturn(Optional.of(planet));

        Optional<Planet> sut = planetService.getById(planet.getId());

        assertTrue(sut.isPresent());
        assertEquals(sut.get(), planet);
    }

    @Test
    public void getPlanet_WithInvalidId_ThrowsException() {
        when(planetRepository.findById(-1L)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> planetService.getById(-1L));
    }

    @Test
    public void getPlanet_WithUnexistingName_ReturnsEmpty() {
        when(planetRepository.findByName("Earth")).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.getByName("Earth");
        assertTrue(sut.isEmpty());
    }

    @Test
    public void getPlanet_WithExistingName_ReturnsPlanet() {
        Planet planet = PlanetFactory.createPlanet();
        when(planetRepository.findByName(planet.getName())).thenReturn(Optional.of(planet));

        Optional<Planet> sut = planetService.getByName(planet.getName());

        assertTrue(sut.isPresent());
        assertEquals(sut.get(), planet);
    }

    @Test
    public void getPlanet_WithInvalidName_ThrowsException() {
        when(planetRepository.findByName("")).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> planetService.getByName(""));
    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        List<Planet> lstPlanets = new ArrayList<>();
        lstPlanets.add(PLANET);

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));

        when(planetRepository.findAll(query)).thenReturn(lstPlanets);

        List<Planet> sut = planetService.listPlanets(PLANET.getClimate(), PLANET.getTerrain());

        assertFalse(sut.isEmpty());
        assertEquals(sut.size(), 1);
        assertEquals(sut.get(0), PLANET);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.listPlanets(PLANET.getClimate(), PLANET.getTerrain());

        assertTrue(sut.isEmpty());
        assertNotEquals(sut, null);
    }

    @Test
    public void deletePlanet_WithExistingId_DoesNotThrowAnyException() {
        assertDoesNotThrow(() -> planetService.delete(1L));
    }

    @Test
    public void deletePlanet_WithUnexistingId_ThrowsException() {
        doThrow(RuntimeException.class).when(planetRepository).deleteById(-1L);
        assertThrows(RuntimeException.class, () -> planetService.delete(-1L));
    }
}