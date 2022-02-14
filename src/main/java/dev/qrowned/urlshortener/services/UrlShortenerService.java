package dev.qrowned.urlshortener.services;

import dev.qrowned.urlshortener.data.UrlData;
import dev.qrowned.urlshortener.repositories.UrlDataRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public final class UrlShortenerService {

    private final UrlDataRepository urlDataRepository;

    public CompletableFuture<UrlData> getUrlData(@NotNull String id) {
        return this.urlDataRepository.findAsyncById(id);
    }

    public CompletableFuture<UrlData> create(@NotNull String url, @NotNull String creator) {
        return CompletableFuture.supplyAsync(() -> this.urlDataRepository.save(new UrlData(url, creator)));
    }

    public CompletableFuture<UrlData> create(@NotNull String id, @NotNull String url, @NotNull String creator) {
        if (this.urlDataRepository.existsById(id)) return this.create(id + "1", url, creator);
        return CompletableFuture.supplyAsync(() -> this.urlDataRepository.save(new UrlData(id, url, creator)));
    }

    public void delete(@NotNull String id, @NotNull String creator) {
        this.urlDataRepository.deleteAsyncByIdAndCreator(id, creator);
    }

}
