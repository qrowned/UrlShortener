package dev.qrowned.urlshortener.repositories;

import dev.qrowned.urlshortener.data.UrlData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface UrlDataRepository extends MongoRepository<UrlData, String> {

    @Async
    CompletableFuture<UrlData> findAsyncById(@NotNull String id);

    @Async
    void deleteAsyncByIdAndCreator(@NotNull String id, @NotNull String creator);

}
