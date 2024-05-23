package be.pxl.parking.api.input;

import jakarta.validation.constraints.NotBlank;

public record ParkingSessionStopCommand(@NotBlank String licensePlate) {
}
