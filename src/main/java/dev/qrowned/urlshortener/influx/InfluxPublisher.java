package dev.qrowned.urlshortener.influx;

import com.influxdb.client.write.Point;

import java.util.List;

public interface InfluxPublisher {

    List<Point> publishPoints();

}
