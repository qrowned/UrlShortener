package dev.qrowned.urlshortener.influx;

import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public final class InfluxPublishRegistry {

    private final InfluxConfig influxConfig;
    private final WriteApiBlocking writeApi;
    private final List<? extends InfluxPublisher> publishers;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public InfluxPublishRegistry(@NotNull InfluxConfig influxConfig, @NotNull List<? extends InfluxPublisher> publishers) {
        this.influxConfig = influxConfig;
        this.writeApi = InfluxDBClientFactory
                .create(influxConfig.getUrl(), influxConfig.getApiKey().toCharArray())
                .getWriteApiBlocking();
        this.publishers = publishers;
        this.scheduleTask();
    }

    public void publishData() {
        this.publishers.forEach(influxPublisher -> {
            List<Point> points = influxPublisher.publishPoints();

            points.forEach(point -> this.writeApi.writePoint(this.influxConfig.getBucket(), this.influxConfig.getOrganization(), point));
        });
    }

    private void scheduleTask() {
        this.executorService.scheduleAtFixedRate(() -> {
            try {
                this.publishData();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

}
