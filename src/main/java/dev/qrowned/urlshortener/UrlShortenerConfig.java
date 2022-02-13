package dev.qrowned.urlshortener;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@Configuration
@ConfigurationProperties(prefix = "urlshortener")
public class UrlShortenerConfig {

    private List<UUID> apiKeys;

    public UrlShortenerConfig() {
        this.apiKeys = Collections.emptyList();
    }
}
