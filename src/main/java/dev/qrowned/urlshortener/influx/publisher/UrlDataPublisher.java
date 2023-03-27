package dev.qrowned.urlshortener.influx.publisher;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import dev.qrowned.urlshortener.influx.InfluxPublisher;
import dev.qrowned.urlshortener.repositories.UrlDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class UrlDataPublisher implements InfluxPublisher {

    private final UrlDataRepository urlDataRepository;

    @Override
    public List<Point> publishPoints() {
        Point point = Point.measurement("url_data")
                .time(Instant.now(), WritePrecision.NS)
                .addField("total", this.urlDataRepository.findAll().size());
        return Collections.singletonList(point);
    }

}
