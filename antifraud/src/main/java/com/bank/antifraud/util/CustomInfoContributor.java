package com.bank.antifraud.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomInfoContributor implements InfoContributor {
    private final Environment environment;
    private final LocalDateTime time = LocalDateTime.now();
    @Autowired
    public CustomInfoContributor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("version", environment.getProperty("info.build.version"))
                .withDetail("artifactId", environment.getProperty("info.build.artifactId"))
                .withDetail("timestamp", time)
                .withDetail("context-path", environment.getProperty("server.servlet.context-path"));
    }
}
