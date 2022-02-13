package dev.qrowned.urlshortener.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "url_data")
public class UrlData {

    @Id
    private String id;

    private String url;
    private UUID creator;

    private long createdAt;

    public UrlData(String url, UUID creator) {
        this.url = url;
        this.creator = creator;
        this.createdAt = System.currentTimeMillis();
    }
}
