package dev.qrowned.urlshortener.services;

import dev.qrowned.urlshortener.data.UrlData;
import dev.qrowned.urlshortener.repositories.UrlDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public final class UrlShortenerService {

    private final UrlDataRepository urlDataRepository;

    public CompletableFuture<UrlData> getUrlData(String id) {
        return this.urlDataRepository.findAsyncById(id);
    }

    public CompletableFuture<UrlData> create(String url, UUID creator) {
        return CompletableFuture.supplyAsync(() -> this.urlDataRepository.save(new UrlData(url, creator)));
    }

    public void delete(String id, UUID creator) {
        this.urlDataRepository.deleteAsyncByIdAndCreator(id, creator);
    }

}
