package dev.qrowned.urlshortener.influx.publisher;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import dev.qrowned.urlshortener.influx.InfluxPublisher;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Component
public final class RequestsPublisher implements InfluxPublisher {

    private int create = 0;
    private int get = 0;

    @Override
    public List<Point> publishPoints() {
        Point point = Point.measurement("requests")
                .time(Instant.now(), WritePrecision.NS)
                .addField("create", this.create)
                .addField("get", this.get);
        this.create = 0;
        this.get = 0;
        return Collections.singletonList(point);
    }

    public int increaseCreate() {
        this.create++;
        return this.create;
    }

    public int increaseGet() {
        this.get++;
        return this.get;
    }

}
