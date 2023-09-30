package com.bank.profile.util;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class Actuator implements InfoContributor {

    private final BuildProperties buildProperties;

    public Actuator(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Override
    public void contribute(Info.Builder builder) {
        final Map<String, Object> customInfo = new HashMap<>();
        customInfo.put("version", buildProperties.getVersion());
        customInfo.put("artifactId", buildProperties.getArtifact());
        customInfo.put("launchTime", LocalDateTime.now().toString());
        customInfo.put("contextPath", "/api/profile");

        builder.withDetail("custom-info", customInfo);
    }
}
