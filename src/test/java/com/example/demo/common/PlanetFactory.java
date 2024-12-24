package com.example.demo.common;

import com.example.demo.entity.Planet;
import com.github.javafaker.Faker;

public class PlanetFactory {
    private static final Faker faker = new Faker();

    public static final Planet PLANET = new Planet(
        "Tatooine",
        "Arid",
        "Desert"
    );

    public static Planet createPlanet() {
        return new Planet(
            faker.space().planet(),
            faker.weather().description(),
            faker.lorem().characters(10)
        );
    }

    public static Planet createInvalidPlanet() {
        return new Planet(
            " ",
            " ",
            " "
        );
    }
}
