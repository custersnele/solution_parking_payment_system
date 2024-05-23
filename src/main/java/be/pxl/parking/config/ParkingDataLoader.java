package be.pxl.parking.config;

import be.pxl.parking.domain.Parking;
import be.pxl.parking.repository.ParkingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class ParkingDataLoader implements CommandLineRunner {
    private static final Logger LOGGER = LogManager.getLogger(ParkingDataLoader.class);
    private final ParkingRepository parkingRepository;

    public ParkingDataLoader(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (parkingRepository.count() > 0) {
            LOGGER.info("parkings already loaded...");
            return;
        }
        LOGGER.info("loading parkings...");
        try (BufferedReader reader = Files.newBufferedReader(Path.of("./src/main/resources/data/parkings.csv"))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                Parking parking = new Parking(UUID.fromString(split[0]), split[1], split[2], Integer.parseInt(split[3]), split[4]);
                parking.setWeekendHourlyRate(Double.parseDouble(split[5]));
                parking.setWeekdayHourlyRate(Double.parseDouble(split[6]));
                parking.setDailyRate(Double.parseDouble(split[7]));
                parkingRepository.save(parking);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
