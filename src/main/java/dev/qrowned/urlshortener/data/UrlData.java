package dev.qrowned.urlshortener.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "url_data")
@NoArgsConstructor
public class UrlData {

    @Id
    private String id = RandomStringUtils.randomAlphanumeric(5);

    private String url;
    private String creator;

    private long createdAt;

    public UrlData(@NotNull String url, @NotNull String creator) {
        this.url = url;
        this.creator = creator;
        this.createdAt = System.currentTimeMillis();
    }

    public UrlData(@NotNull String id, @NotNull String url, @NotNull String creator) {
        this.id = id;
        this.url = url;
        this.creator = creator;
        this.createdAt = System.currentTimeMillis();
    }
}
