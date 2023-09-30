package com.bank.profile.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerConfig {

    @SuppressWarnings(value = "all")
    public static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    static void start() {
        System.out.println("Starting the container...");
        database.start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        System.out.println("Configuring properties...");
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }

}
