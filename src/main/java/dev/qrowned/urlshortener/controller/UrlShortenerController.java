package dev.qrowned.urlshortener.controller;

import dev.qrowned.urlshortener.data.UrlData;
import dev.qrowned.urlshortener.influx.publisher.RequestsPublisher;
import dev.qrowned.urlshortener.services.UrlShortenerService;
import io.sentry.spring.jakarta.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final RequestsPublisher requestsPublisher;
    private final UrlShortenerService urlShortenerService;

    @Async
    @SentrySpan
    @GetMapping("{id}/**") // using "/**" to allow access with slash and without slash
    public Future<ResponseEntity<Void>> redirect(@PathVariable String id) {
        this.requestsPublisher.increaseGet();

        return this.urlShortenerService.getUrlData(id)
                .handleAsync((urlData, throwable) -> {
                    if (urlData == null) return ResponseEntity.notFound().build();

                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(urlData.getUrl()))
                            .build();
                });
    }

    @Async
    @PostMapping("create/")
    public Future<UrlData> create(@RequestParam URI url, @RequestHeader("API-KEY") String apiKey) throws MalformedURLException {
        this.requestsPublisher.increaseCreate();
        return this.urlShortenerService.create(url.toURL().toString(), apiKey);
    }

    @Async
    @PostMapping("createWithId/")
    public Future<UrlData> create(@RequestParam String id, @RequestParam URI url, @RequestHeader("API-KEY") String apiKey) throws MalformedURLException {
        this.requestsPublisher.increaseCreate();
        return this.urlShortenerService.create(id, url.toURL().toString(), apiKey);
    }

    @Async
    @DeleteMapping("delete/")
    public void delete(@RequestParam String id, @RequestHeader("API-KEY") String apiKey) {
        this.urlShortenerService.delete(id, apiKey);
    }

}
