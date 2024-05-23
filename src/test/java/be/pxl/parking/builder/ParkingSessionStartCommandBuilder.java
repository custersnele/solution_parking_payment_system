package be.pxl.parking.builder;

import be.pxl.parking.api.input.ParkingSessionStartCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public final class ParkingSessionStartCommandBuilder {
    private @NotBlank String licensePlate;
    private @NotNull UUID parking;

    private ParkingSessionStartCommandBuilder() {
    }

    public static ParkingSessionStartCommandBuilder aParkingSessionStartCommand() {
        return new ParkingSessionStartCommandBuilder();
    }

    public ParkingSessionStartCommandBuilder withLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public ParkingSessionStartCommandBuilder withParking(UUID parking) {
        this.parking = parking;
        return this;
    }

    public ParkingSessionStartCommand build() {
        return new ParkingSessionStartCommand(licensePlate, parking);
    }
}
