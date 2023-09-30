package com.bank.account.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Actuator implements InfoContributor {

    private final String artifactId;
    private final String contextPath;
    private final String version;

    private LocalDateTime time = LocalDateTime.now();

    public Actuator(@Value("${info.build.artifactId}") String artifactId,
                    @Value("${server.servlet.context-path}") String contextPath,
                    @Value("${info.build.version}") String version) {
        this.artifactId = artifactId;
        this.contextPath = contextPath;
        this.version = version;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder
                .withDetail("version", version)
                .withDetail("artifactId", artifactId)
                .withDetail("time", time)
                .withDetail("contextPath", contextPath);
    }
}
