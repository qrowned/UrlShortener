package dev.qrowned.urlshortener;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@Configuration
@ConfigurationProperties(prefix = "urlshortener")
public class UrlShortenerConfig {

    private List<String> apiKeys;

    public UrlShortenerConfig() {
        this.apiKeys = Collections.singletonList(UUID.randomUUID().toString());
    }
}
