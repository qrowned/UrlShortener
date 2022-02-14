package dev.qrowned.urlshortener.controller;

import dev.qrowned.urlshortener.data.UrlData;
import dev.qrowned.urlshortener.services.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Async
    @GetMapping("{id}")
    public Future<ResponseEntity<Void>> redirect(@PathVariable String id) {
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
    public Future<UrlData> create(@RequestParam String url, @RequestHeader("API-KEY") String apiKey) {
        return this.urlShortenerService.create(url, apiKey);
    }

    @Async
    @PostMapping("createWithId/")
    public Future<UrlData> create(@RequestParam String id, @RequestParam String url, @RequestHeader("API-KEY") String apiKey) {
        return this.urlShortenerService.create(id, url, apiKey);
    }

    @Async
    @DeleteMapping("delete/")
    public void delete(@RequestParam String id, @RequestHeader("API-KEY") String apiKey) {
        this.urlShortenerService.delete(id, apiKey);
    }

}
