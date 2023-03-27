package dev.qrowned.urlshortener.influx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "influx")
public class InfluxConfig {

    private String url;
    private String apiKey;
    private String organization;
    private String bucket;

    public InfluxConfig() {
    }

}
