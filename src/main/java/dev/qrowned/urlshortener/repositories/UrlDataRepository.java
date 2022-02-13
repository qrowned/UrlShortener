package dev.qrowned.urlshortener.repositories;

import dev.qrowned.urlshortener.data.UrlData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface UrlDataRepository extends MongoRepository<UrlData, String> {

    @Async
    CompletableFuture<UrlData> findAsyncById(String id);

    @Async
    CompletableFuture<Void> deleteAsyncByIdAndCreator(String id, UUID creator);

}
