package be.pxl.parking.config;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class SystemClock {
    private final LocalDateTime startTime;
    private static final int TIME_SCALE = 3600; // 1 second = 1 hour in simulation time

    public SystemClock() {
        this.startTime = LocalDateTime.now();
    }

    public LocalDateTime getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedRealTime = Duration.between(startTime, currentTime);
        long elapsedSeconds = elapsedRealTime.getSeconds();
        return startTime.plusSeconds(elapsedSeconds * TIME_SCALE);
    }
}