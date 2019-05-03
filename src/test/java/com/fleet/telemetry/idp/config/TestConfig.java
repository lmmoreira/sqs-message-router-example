package com.fleet.telemetry.idp.config;

import com.github.javafaker.Faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@TestConfiguration
@EnableAutoConfiguration
public class TestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConfig.class);

    @Bean
    public Faker faker() {
        final long seed = 6270435371033L; // Retrieved by calling System.nanoTime()
        LOGGER.info("Using seed {} as Faker random seed", seed);
        return Faker.instance(new Random(seed));
    }

}
