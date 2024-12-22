package com.example.demo.common;

import com.example.demo.entity.Planet;
import com.github.javafaker.Faker;

public class PlanetFactory {
    private static final Faker faker = new Faker();

    public static Planet create() {
        return new Planet(
            faker.space().planet(),
            faker.weather().description(),
            faker.lorem().characters(10)
        );
    }
}
