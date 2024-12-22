package com.example.demo.service;

import com.example.demo.common.PlanetFactory;
import com.example.demo.entity.Planet;
import com.example.demo.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
// @SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {
    @InjectMocks private PlanetService planetService;
    @Mock private PlanetRepository planetRepository;

    @Test
    public void createPlanet_WithValidData_ReturnsCreatedPlanet() {
        Planet planet = PlanetFactory.create();

        when(planetRepository.save(planet)).thenReturn(planet);

        // System Under Test
        Planet sut = planetService.create(planet);

        assertEquals(sut, planet);
    }
}