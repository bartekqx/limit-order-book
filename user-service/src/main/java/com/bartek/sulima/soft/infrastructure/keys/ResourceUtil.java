package com.bartek.sulima.soft.infrastructure.keys;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
class ResourceUtil {

    private final ResourceLoader resourceLoader;

    public ResourceUtil(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String asString(String resourcePath) throws IOException {
        final Resource resource = resourceLoader.getResource(resourcePath);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
}